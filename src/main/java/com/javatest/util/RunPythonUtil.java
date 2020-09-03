package com.javatest.util;

import org.apache.commons.lang3.StringUtils;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * java执行python的工具类
 *  使用条件，python代码中有一个main方法，其他代码都在main方法中执行
 * @author azure
 */

@Component
public class RunPythonUtil {

    private static Logger logger = LoggerFactory.getLogger(RunPythonUtil.class);

    /**
     * 工具类的方法都是静态方法，在静态方法中调用ioc容器中的bean时需要特别的改造
     */
    @Autowired
    private ConfigProperties configProperties;
    private static RunPythonUtil runPythonUtil;

    private static String parseCharset;

    static{
        // windows默认的编码是GBK，linux默认的编码是utf-8，所以解析脚本执行完返回的数据时需要根据系统来决定码表，否则会中文乱码
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            parseCharset = "GBK";
        } else if (os.contains("linux")) {
            parseCharset = "UTF-8";
        } else {
            parseCharset = null;
        }
    }

    public void setConfigProperties(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @PostConstruct
    public void init() {
        runPythonUtil = this;
        runPythonUtil.configProperties = this.configProperties;
    }

    /**
     * 使用jython运行py代码，缺点：一旦引用第三方库容易报错，而即便手动设置第三方库，也有可能出现错误
     * @param script python解析代码
     * @param params python代码中的参数
     * @return
     */
    public static Map<String,Object> runPythonByJython(String script, String params){
        System.out.println("*****************使用jython解析*****************");
        Map<String,Object> rtnMap = new HashMap<>();

        Properties props = new Properties();
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
//        // 下面是加入jython的库，需要指定jar包所在的路径。如果有自己写的包或者第三方，可以继续追加
//        interpreter.exec("import sys");
//        interpreter.exec("sys.path.append('C:/jython2.7.1/Lib')");
//        interpreter.exec("sys.path.append('C:/jython2.7.1/Lib/site-packages')");
        try {
            interpreter.exec(script);
            // 假设python只有一个main方法，所有代码都在main方法内
            PyFunction function = interpreter.get("main",PyFunction.class);
            // 将报文代入并执行python进行解析
            PyObject o = function.__call__(Py.newString(params));
            rtnMap.put("result",o);
            interpreter.cleanup();
            interpreter.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("jython解析报错：" + e.getMessage());
            rtnMap.put("error",e.toString());
        }
        System.out.println("*****************完成jython解析*****************");
        return rtnMap;
    }

    /**
     * 使用Runtime.getRuntime().exec()解析运行python
     * @param command 解析的python代码，即py文件的路径
     * @param params python代码中的参数
     * @param charset 码表
     * @return
     */
    public static Map<String,Object> runPythonByRuntime(String command, String params, String charset) {
        System.out.println("*****************使用runtime解析*****************");
        Map<String,Object> rtnMap = new HashMap<>();
        String line;
        StringBuilder rtnSb = new StringBuilder();
        try {
            /* 注意：cmd的格式：“python py文件的路径 参数...”
             *  注意2：参数是字符串的时候，有可能会出现参数只解析第一个词的情况，此时必须在整个字符串参数首尾手动添加双引号（单引号都不行）
             *  则下面的cmd=python E:/test/pythontest/Demo.py “params”
             */

            //String cmd = String.format("python %s \"%s\"",command,params);
            // 也可以用String[]，但是params传入前也需要手动在字符串前后加双引号
            params = "\"" + params + "\"";
            String[] cmd = new String[]{"python",command,params};
            Process process = Runtime.getRuntime().exec(cmd);
            // error的要单独开一个线程处理。其实最好分成两个子线程处理标准输出流和错误输出流
            ProcessStream stderr = new ProcessStream(process.getErrorStream(), "ERROR", charset);
            stderr.start();
            // 获取标准输出流的内容
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
            while ((line = stdout.readLine()) != null) {
                rtnSb.append(line).append("\r\n");
            }
            rtnMap.put("result",rtnSb.toString());
            rtnMap.put("error",stderr.getContent());
            //关闭流
            stdout.close();
            int status = process.waitFor();
            if (status != 0) {
                System.out.println("return value:"+status);
                logger.info("event:{}", "RunExeForWindows",process.exitValue());
            }
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("runtime解析报错：" + e.getMessage());
        }
        System.out.println("*****************完成runtime解析*****************");
        return rtnMap;
    }

    /**
     * 使用Runtime.getRuntime().exec()解析运行python，如果频繁请求，可能会增大磁盘io压力和影响磁盘寿命
     * @param script 解析的python代码,会自动将代码保存成文件
     * @param params python代码中的参数
     * @param charset 指定系统生成文件所用码表
     * @return
     */
    public static Map<String,Object> runPythonFileByRuntime(String script, String params, String charset) {
        System.out.println("*****************使用runtime解析,包含缓存文件*****************");

        Map<String,Object> rtnMap = new HashMap<>();
        // 缓存py文件
        String command = null;
        BufferedWriter bw = null;
        File file = null;
        if (StringUtils.isBlank(charset)) {
            charset = "UTF-8";
        }
        try {
            file = new File( runPythonUtil.configProperties.getPythonFilePath() + "py" + System.currentTimeMillis() + ".py");
            if (!file.exists()) {
                file.createNewFile();
            }
            command = file.getAbsolutePath();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
            bw.write(script);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("runtime生成py脚本文件报错：" + e.getMessage());
            rtnMap.put("errorMsg", "无法保存py文件！");
            return rtnMap;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        File paramFile = null;
        // 如果参数长度太大，则可能会超过cmd的长度限制导致执行失败，此时要将参数转存为文件
        if (params.length() < 1024) {   // 1024是自定义的，可以根据系统性能或设置来定义。注意，windows的cmd不能超过8196
            params = "\"" + params + "\"";  // 以防万一，虽然本机运行有可能不加前后双引号也能执行（原因不明），但极可能换主机后会出现返回码为1的错误
        } else {
            try {
                String paramsPath = runPythonUtil.configProperties.getPythonFilePath() + "param" + System.currentTimeMillis() + ".txt";
                paramFile = new File(paramsPath);
                if (!paramFile.exists()) {
                    paramFile.createNewFile();
                }
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramFile), charset));
                bw.write(params);
                params = paramsPath;    // 此时参数为参数文件的绝对路径
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("runtime生成txt参数文件报错：" + e.getMessage());
                rtnMap.put("errorMsg", "无法保存参数文件！");
                return rtnMap;
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // 用刚才缓存的py文件解析代码
        String line;
        StringBuilder rtnSb = new StringBuilder();
        try {
            String[] cmd = new String[]{"python",command,params};
            Process process = Runtime.getRuntime().exec(cmd);
            // error的要单独开一个线程处理。其实最好分成两个子线程处理标准输出流和错误输出流
            ProcessStream stderr = new ProcessStream(process.getErrorStream(), "ERROR", parseCharset);
            stderr.start();
            // 获取标准输出流的内容
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), parseCharset));
            while ((line = stdout.readLine()) != null) {
                rtnSb.append(line).append("\r\n");
            }
            rtnMap.put("result",rtnSb.toString());
            rtnMap.put("error",stderr.getContent());
            //关闭流
            stdout.close();
            int status = process.waitFor();
            if (status != 0) {
                System.out.println("return value:"+status);
                logger.info("event:{}", "RunExeForWindows",process.exitValue());
            }
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("runtime解析报错：" + e.getMessage());
        }
        // 如果只是一次性的执行，可以将生成的py文件删除，看情况
        // 使用file的delete方法时，一定要判断清楚文件路径。这样的删除很难恢复，万一路径是磁盘路径或者重要文件夹路径，等着哭吧
        try {
            if (file!=null) {
                file.delete();
            }
            if (paramFile!=null) {
                paramFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("runtime解析报错：" + e.getMessage());
        }
        System.out.println("*****************完成runtime解析,包含缓存文件*****************");
        return rtnMap;
    }

    /**
     * 使用Runtime.getRuntime().exec()解析运行python，如果频繁请求，可能会增大磁盘io压力和影响磁盘寿命
     * @param command   统一请求解析文件的路径，也就是door.py
     * @param feedback  需要解析的报文字符串
     * @param analysis  用户编写的解析字符串，允许为空，按原来的解析文件解析，但此时packetName不能为空
     * @param packetName    用户编写的解析文件的路径，允许为空，但此时analysis不能为空
     * @param charset       码表
     * @return
     */
    public static Map<String,Object> runPythonFileByRuntime4CU(String command, String feedback, String analysis, String packetName, String charset) {
        System.out.println("*****************使用runtime解析,包含缓存文件*****************");

        Map<String,Object> rtnMap = new HashMap<>();
        if (StringUtils.isBlank(command)) {
            command = runPythonUtil.configProperties.getPythonFilePath() + "door2.py";
        }
        if (StringUtils.isBlank(charset)) {
            charset = "UTF-8";
        }
        BufferedWriter bw = null;
        File file = null;
        // 将用户编写的解析字符串生成py文件，并返回py文件名，下次传入文件名就不需重新生成新的py文件
        if (StringUtils.isBlank(analysis)) {
            if (StringUtils.isBlank(packetName)) {
                rtnMap.put("errorMsg", "解析代码不能为空！");
                return rtnMap;
            }
        } else {
            if (StringUtils.isBlank(packetName)) {
                packetName = "py" + System.currentTimeMillis();
            }
            try {
                file = new File(runPythonUtil.configProperties.getPythonFilePath() + packetName + ".py");
                if (!file.exists()) {
                    file.createNewFile();
                }
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
                bw.write(analysis);
                rtnMap.put("pyFileName", packetName);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("runtime生成py脚本文件报错：" + e.getMessage());
                rtnMap.put("errorMsg", "无法保存py文件！");
                return rtnMap;
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 如果参数长度太大，则可能会超过cmd的长度限制导致执行失败，此时要将参数转存为文件
        String params = null;   // 用来存放真正执行在cmd命令行上的参数
        String flag = "0";  // 用来判断cmd上参数是报文还是文件路径 0-报文，1-路径
        File paramFile = null;

        if (feedback.length() < 1024) {   // 1024是自定义的，可以根据系统性能或设置来定义。注意，windows的cmd不能超过8196
            params = "\"" + feedback + "\"";  // 以防万一，虽然本机运行有可能不加前后双引号也能执行（原因不明），但极可能换主机后会出现返回码为1的错误
        } else {
            try {
                String paramsPath = runPythonUtil.configProperties.getPythonFilePath() + "param" + System.currentTimeMillis() + ".txt";
                paramFile = new File(paramsPath);
                if (!paramFile.exists()) {
                    paramFile.createNewFile();
                }
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(paramFile), charset));
                bw.write(feedback);
                params = paramsPath;    // 此时参数为参数文件的绝对路径
                flag = "1";
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("runtime生成txt参数文件报错：" + e.getMessage());
                rtnMap.put("errorMsg", "无法保存参数文件！");
                return rtnMap;
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        String line;
        StringBuilder rtnSb = new StringBuilder();
        try {
            String pythonExe = runPythonUtil.configProperties.getPythonExe();
            String[] cmd = new String[]{pythonExe,command,packetName,params,flag};
            Process process = Runtime.getRuntime().exec(cmd);
            ProcessStream stderr = new ProcessStream(process.getErrorStream(), "ERROR", parseCharset);
            stderr.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), parseCharset));
            while ((line = stdout.readLine()) != null) {
                rtnSb.append(line).append("\r\n");
            }
            rtnMap.put("result",rtnSb.toString());
            rtnMap.put("error",stderr.getContent());
            stdout.close();
            int status = process.waitFor();
            if (status != 0) {
                System.out.println("return value:"+status);
                logger.info("event:{}", "RunExeForWindows",process.exitValue());
            }
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("runtime解析报错：" + e.getMessage());
        }
        try {
            if (file!=null) {
                file.delete();
            }
            if (paramFile!=null) {
                paramFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("runtime解析报错：" + e.getMessage());
        }
        System.out.println("*****************完成runtime解析,包含缓存文件*****************");
        return rtnMap;
    }

//    /**
//     * 使用Runtime.getRuntime().exec()解析运行python字符串代码（废弃）
//     *  从原理上看应该是无法实现的，因为是模拟cmd命令行的方式执行，而cmd只能执行命令，不能执行代码
//     * @param script 解析的python代码字符串
//     * @param params python代码中的参数
//     * @param charset 码表
//     * @return
//     */
//    public static Map<String,Object> runPythonByRuntime2(String script, String params, String charset) {
//        Map<String,Object> rtnMap = new HashMap<>();
//        String line;
//        StringBuffer rtnSb = new StringBuffer();
//        try {
//            /*
//             * 以下代码是无法实现的，*废弃*
//             */
//            script = "\"" + script + "\"";
////            String cmd = String.format(script,params);
//            Process process = Runtime.getRuntime().exec(script);
//            // error的要单独开一个线程处理。其实最好分成两个子线程处理标准输出流和错误输出流
//            ProcessStream stderr = new ProcessStream(process.getErrorStream(), "ERROR", charset);
//            stderr.start();
//            // 获取标准输出流的内容
//            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
//            while ((line = stdout.readLine()) != null) {
//                rtnSb.append(line).append("\r\n");
//            }
//            rtnMap.put("result",rtnSb.toString());
//            rtnMap.put("error",stderr.getContent());
//            //关闭流
//            stdout.close();
//            int status = process.waitFor();
//            if (status != 0) {
//                System.out.println("return value:"+status);
//                logger.info("event:{}", "RunExeForWindows",process.exitValue());
//            }
//            process.destroy();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rtnMap;
//    }
    public static void main(String[] args) throws IOException {
        String[] cmd = new String[]{};
        Process exec = Runtime.getRuntime().exec(cmd);
    }

}

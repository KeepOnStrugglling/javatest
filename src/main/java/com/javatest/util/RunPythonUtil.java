package com.javatest.util;

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
 * @author hjw
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
        StringBuffer rtnSb = new StringBuffer();
        try {
            /* 注意：cmd的格式：“python py文件的路径 参数...”
             *  注意2：参数是字符串的时候，必须在首尾手动添加双引号（单引号都不行）
             *  则下面的cmd=python E:/test/pythontest/Demo.py “params” */
//            String cmd = String.format("python %s \"%s\"",command,params);
            // 也可以用String[]，但是params传入前也需要手动在字符串前后加双引号
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
     * 使用Runtime.getRuntime().exec()解析运行python，如果频繁请求，可能会增大磁盘io压力
     * @param script 解析的python代码,会自动将代码保存成文件
     * @param params python代码中的参数
     * @param charset 码表
     * @return
     */
    public static Map<String,Object> runPythonFIleByRuntime(String script, String params, String charset) {
        System.out.println("*****************使用runtime解析,包含缓存文件*****************");

        Map<String,Object> rtnMap = new HashMap<>();
        // 缓存py文件
        String command = null;
        FileWriter fw = null;
        File file = null;
        try {
            file = new File( runPythonUtil.configProperties.getPythonFilePath() + "py" + new Date().getTime() + ".py");
            if (!file.exists()) {
                file.createNewFile();
            }
            command = file.getAbsolutePath();
            fw = new FileWriter(file);
            fw.write(script);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("runtime解析报错：" + e.getMessage());
            rtnMap.put("errorMsg", "无法保存py文件！");
            return rtnMap;
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 用刚才缓存的py文件解析代码
        String line;
        StringBuffer rtnSb = new StringBuffer();
        try {
            /* 注意：cmd的格式：“python py文件的路径 参数...”
             *  注意2：参数是字符串的时候，必须在首尾手动添加双引号（单引号也不行） */
            // params传入前需要手动在字符串前后加双引号
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
        // 如果只是一次性的执行，可以将生成的py文件删除，看情况
        // 使用file的delete方法时，一定要判断清楚文件路径。这样的删除很难恢复，万一路径是磁盘路径或者重要文件夹路径，等着哭吧
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("runtime解析报错：" + e.getMessage());
        }
        System.out.println("*****************完成runtime解析,包含缓存文件*****************");
        return rtnMap;
    }

    /**
     * 使用Runtime.getRuntime().exec()解析运行python字符串代码（废弃）
     *  从原理上看应该是无法实现的，因为是模拟cmd命令行的方式执行，而cmd只能执行命令，不能执行代码
     * @param script 解析的python代码字符串
     * @param params python代码中的参数
     * @param charset 码表
     * @return
     */
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

    /**
     * 附测试代码
     */
    public static void main(String[] args) {

        /*
        // 测试使用jython解析
        String script = "# # -*- coding: utf-8 -*-\n" +
                "\n" +
                "status=0\n" +
                "succParamMsg = \"\"\n" +
                "textMsg = \"\"\n" +
                "errorMsg = \"\"\n" +
                "returnMsg = {\"status\":status,\"succParamMsg\":succParamMsg,\"textMsg\":textMsg,\"errorMsg\":errorMsg}\n" +
                "\n" +
                "def main(report):\n" +
                "    if(1<2):\n" +
                "        status=1\n" +
                "    returnMsg[\"succParamMsg\"]=report\n" +
                "    print(returnMsg)\n" +
                "    return returnMsg";
        String report = "hello world";
        Map<String, Object> map = runPythonByJython(script, report);
        System.out.println("result:" + map.get("result"));

//        String script = "# coding=utf-8\n" +
//                "\n" +
//                "status=0\n" +
//                "succParamMsg = \"\"\n" +
//                "textMsg = \"\"\n" +
//                "errorMsg = \"\"\n" +
//                "returnMsg = {\"status\":status,\"succParamMsg\":succParamMsg,\"textMsg\":textMsg,\"errorMsg\":errorMsg}\n" +
//                "\n" +
//                "def main(report):\n" +
//                "    if(1<2):\n" +
//                "        status=1\n" +
//                "    returnMsg[\"succParamMsg\"]=report\n" +
//                "    print(returnMsg)\n" +
//                "    return returnMsg";
//        String script2 = "import ast\n" +
//                "\n" +
//                "status=0\n" +
//                "succParamMsg = \"\"\n" +
//                "textMsg = \"\"\n" +
//                "errorMsg = \"\"\n" +
//                "returnMsg = {\"status\":status,\"succParamMsg\":succParamMsg,\"textMsg\":textMsg,\"errorMsg\":errorMsg}\n" +
//                "\n" +
//                "eval(\"__import__('os').system('whoami')\")\n" +
//                "\n" +
//                "def main(report):\n" +
//                "    ast.literal_eval(\"__import__('os').system('whoami')\")\n" +
//                "    returnMsg[\"succParamMsg\"] = report\n" +
//                "    print(returnMsg)\n" +
//                "    return returnMsg";
//        Map<String, Object> map = runPythonByJython(script2, params);
//        System.out.println("result:" + map.get("result"));
//        System.out.println("error:" + map.get("error"));
        */

        // 测试使用runtime.exec解析
        String command = "E:/test/pythontest/Demo.py";
        String params = "+++     NMS SERVER        2019-11-07 17:58:47\n" +
                "O&M     #2304\n" +
                "%%LGI:OP=\"aaa\", PWD=\"*****\";%%\n" +
                "RETCODE = 1  \n" +
                "\n" +
                "该用户已经登录\n" +
                "\n" +
                "---    END";
        String charset = "GBK";
        Map<String, Object> map2 = runPythonByRuntime(command, params,charset);
        System.out.println("result:" + map2.get("result"));
        System.out.println("error:" + map2.get("error"));

    }
}

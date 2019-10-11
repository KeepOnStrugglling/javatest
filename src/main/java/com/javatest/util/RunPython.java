package com.javatest.util;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * java执行python的工具类
 *  使用条件，python代码中有一个main方法，其他代码都在main方法中执行
 * @author hjw
 */

public class RunPython {

    private static Logger logger = LoggerFactory.getLogger(RunPython.class);

    /**
     * 使用jython运行py代码，缺点：一旦引用第三方库容易报错，而即便手动设置第三方库，也有可能出现错误
     * @param script python解析代码
     * @param report 报文
     * @return
     */
    public static Map<String,Object> runPythonByJython(String script, String report){
        Map<String,Object> rtnMap = new HashMap<>();

        Properties props = new Properties();
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        try {
            interpreter.exec(script);
            // 假设python只有一个main方法，所有代码都在main方法内
            PyFunction function = interpreter.get("main",PyFunction.class);
            // 将报文代入并执行python进行解析
            PyObject o = function.__call__(Py.newString(report));
            rtnMap.put("result",o);
            interpreter.cleanup();
            interpreter.close();
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("error",e.getMessage());
        }
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
        }
        return rtnMap;
    }

    /**
     * 使用Runtime.getRuntime().exec()解析运行python
     * @param command 解析的python代码字符串
     * @param params python代码中的参数
     * @param charset 码表
     * @return
     */
    public static Map<String,Object> runPythonByRuntime2(String command, String params, String charset) {
        Map<String,Object> rtnMap = new HashMap<>();
        String line;
        StringBuffer rtnSb = new StringBuffer();
        try {
            /* 注意：cmd的格式：“python py文件的路径 参数...”
             *  注意2：参数是字符串的时候，必须在首尾手动添加双引号（单引号都不行）
             *  则下面的cmd=python E:/test/pythontest/Demo.py “params” */
//            String cmd = String.format("python %s \"%s\"",command,params);
            // 也可以用String[]，但是params传入前也需要手动在字符串前后加双引号
            command = "\"" + command + "\"";
            String cmd = String.format("python",command,params);
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
        }
        return rtnMap;
    }

    /**
     * 附测试代码
     */
    public static void main(String[] args) {

        /*
        // 测试使用jython解析
        String script = "# coding=utf-8\n" +
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
        */

        // 测试使用runtime.exec解析
        String command = "E:/test/pythontest/Demo.py";
        String script = "# coding=utf-8\n" +
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
        String params = "\"this is a brand new day\r\nhappy~\"";
        String charset = "utf-8";
//        Map<String, Object> map2 = runPythonByRuntime(command, params,charset);
        Map<String, Object> map2 = runPythonByRuntime2(script, params,charset);
        System.out.println("result:" + map2.get("result"));
        System.out.println("error:" + map2.get("error"));

    }
}

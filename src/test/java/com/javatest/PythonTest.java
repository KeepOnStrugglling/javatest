package com.javatest;

import com.javatest.service.RunPythonService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaTestApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PythonTest {

    @Autowired
    private RunPythonService pythonService;

    /**
     * ScriptEngineManager没有python的解析器，测试失败
     */
    /*@Test
    public void testAnalysePython() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("python");
        try {
            String funcMsg = "status=0\n" +
                    "succParamMsg = \"\"\n" +
                    "textMsg = \"\"\n" +
                    "errorMsg = \"\"\n" +
                    "returnMsg = {\"status\":status,\"succParamMsg\":succParamMsg,\"textMsg\":textMsg,\"errorMsg\":errorMsg}\n" +
                    "\n" +
                    "def main(report):\n" +
                    "    if(1<2):\n" +
                    "        status=1\n" +
                    "    returnMsg[\"succParamMsg\"]=report\n" +
                    "    print(succParamMsg)\n" +
                    "    print(returnMsg)\n" +
                    "    return returnMsg";
            System.out.println(funcMsg);
            engine.eval(funcMsg);
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                System.out.println(in.invokeFunction("main", "test test 111"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void test(){
        String script = "# coding=utf-8\n" +
                "import sys\n" +
                "\n" +
                "def main(report):\n" +
                "    status = '0'\n" +
                "    succParamMsg = \"\"\n" +
                "    textMsg = \"\"\n" +
                "    errorMsg = \"\"\n" +
                "    returnMsg = {\"status\": status, \"succParamMsg\": succParamMsg, \"textMsg\": textMsg, \"errorMsg\": errorMsg}\n" +
                "    if (report.find(\"RETCODE = 0\") == -1):\n" +
                "        returnMsg[\"status\"] = '1'\n" +
                "    returnMsg[\"succParamMsg\"] = report\n" +
                "    print(returnMsg)\n" +
                "    return returnMsg\n" +
                "\n" +
                "main(sys.argv[1])";
        System.out.println(pythonService.saveScript2Py(script));
    }

}

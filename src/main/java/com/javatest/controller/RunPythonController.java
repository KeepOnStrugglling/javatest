package com.javatest.controller;

import com.javatest.service.RunPythonService;
import com.javatest.annotation.OperLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/python")
public class RunPythonController {

    @Autowired
    private RunPythonService service;

    @RequestMapping("/runPython")
    public Map<String,Object> runPython(String script,String code) {
        if (StringUtils.isBlank(script) || StringUtils.isBlank(code)) {
            return null;
        }
        return service.runPython(code,script);
    }

    @RequestMapping("/runPythonByRuntime")
    public Map<String,Object> runPythonByRuntime(String script,String code) {
        Map<String,Object> result = new HashMap<>();
        if (StringUtils.isBlank(script) || StringUtils.isBlank(code)) {
            return null;
        }
        String command = service.saveScript2Py(script);
        if (command == null) {
           result.put("status","fail");
           return result;
        } else {
            return service.runPythonByRuntime(command,code);
        }
    }

    @OperLog(type = "python解析",module = "python",requestDes = "解析脚本为文件，解析报文为字符串")
    @RequestMapping("/runPythonFile")
    public Map<String,Object> runPythonFile(String script, String code, String charset) {
        if (StringUtils.isBlank(script) || StringUtils.isBlank(code)) {
            return null;
        }
        if (StringUtils.isBlank(charset)) {
            charset = "GBK";    // windows环境下命令行用utf-8遇到中文会乱码，建议win环境下用GBK
        }
        return service.runPythonFile(script,code,charset);
    }

    @OperLog(type = "python解析",module = "python",requestDes = "解析脚本为文件，解析报文为文件")
    @RequestMapping("/runPythonFile4CU")
    public Map<String,Object> runPythonFile4CU(String script, String code, String charset) {
        long l1 = System.currentTimeMillis();
        if (StringUtils.isBlank(script) || StringUtils.isBlank(code)) {
            return null;
        }
        if (StringUtils.isBlank(charset)) {
            charset = "GBK";    // windows环境下命令行用utf-8遇到中文会乱码，建议win环境下用GBK
        }
        Map<String, Object> map = service.runPythonFile4CU(script, code, charset);
        long l2 = System.currentTimeMillis();
        System.out.println("耗时：" + (l2-l1));
        return map;
    }
}

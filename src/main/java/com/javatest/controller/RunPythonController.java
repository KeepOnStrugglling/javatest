package com.javatest.controller;

import com.javatest.service.RunPythonService;
import org.apache.commons.lang.StringUtils;
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

    @RequestMapping("/runPythonFile")
    public Map<String,Object> runPythonFile(String script,String code) {
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
}

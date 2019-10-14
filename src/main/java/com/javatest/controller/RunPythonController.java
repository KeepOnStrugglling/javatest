package com.javatest.controller;

import com.javatest.service.RunPythonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
}

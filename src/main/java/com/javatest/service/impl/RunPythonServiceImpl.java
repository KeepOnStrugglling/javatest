package com.javatest.service.impl;

import com.javatest.service.RunPythonService;
import com.javatest.util.RunPython;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RunPythonServiceImpl implements RunPythonService {

    @Override
    public Map<String, Object> runPython(String code, String script) {
        Map<String, Object> rtnMap = RunPython.runPythonByJython(script, code);
        if (rtnMap.get("result")==null) {
            rtnMap.put("status","fail");
        } else {
            rtnMap.put("status","success");
        }
        return rtnMap;
    }
}

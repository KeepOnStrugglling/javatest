package com.javatest.service;

import java.util.Map;

public interface RunPythonService {

    Map<String,Object> runPython(String code, String script);

    String saveScript2Py(String script);

    Map<String, Object> runPythonByRuntime(String command, String code);
}

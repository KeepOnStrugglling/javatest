package com.javatest.service;

import java.util.Map;

public interface RunPythonService {

    Map<String,Object> runPython(String code, String script);
}

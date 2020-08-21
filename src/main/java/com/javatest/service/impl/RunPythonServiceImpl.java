package com.javatest.service.impl;

import com.javatest.mapper.StudentScoreMapper;
import com.javatest.domain.StudentScore;
import com.javatest.service.RunPythonService;
import com.javatest.util.ConfigProperties;
import com.javatest.util.RunPythonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Service
public class RunPythonServiceImpl implements RunPythonService {

    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private StudentScoreMapper studentScoreMapper;

    @Override
    public Map<String, Object> runPython(String code, String script) {
        Map<String, Object> rtnMap = RunPythonUtil.runPythonByJython(script, code);
        if (rtnMap.get("result")==null) {
            rtnMap.put("status","fail");
        } else {
            rtnMap.put("status","success");
        }
        return rtnMap;
    }

    /**
     * 将脚本代码保存为py文件
     * @param script
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveScript2Py(String script) {
        String command = null;
        try {
            File file = new File( configProperties.getPythonFilePath() + "py" + System.currentTimeMillis() + ".py");
            if (!file.exists()) {
                file.createNewFile();
            }
            command = file.getAbsolutePath();
            FileWriter fw = new FileWriter(file);
            fw.write(script);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return command;
    }

    /**
     * 根据保存的py文件进行代码解析
     * @param command
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> runPythonByRuntime(String command, String code) {
        Map<String, Object> rtnMap = RunPythonUtil.runPythonByRuntime(command, code,"GBK");
        if (rtnMap.get("result")==null) {
            rtnMap.put("status","fail");
        } else {
            rtnMap.put("status","success");
        }
        return rtnMap;
    }

    /**
     * RunPythonUtil中提供保存和执行python脚本的方法
     * @param script
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> runPythonFile(String script, String code, String charset) {
        Map<String, Object> rtnMap = RunPythonUtil.runPythonFileByRuntime(script, code,charset);
        if (rtnMap.get("result")==null) {
            rtnMap.put("status","fail");
        } else {
            rtnMap.put("status","success");
        }
        return rtnMap;
    }

    @Override
    public void doUpdate() {
        StudentScore s = new StudentScore();
        s.setId(10015L);
        s.setScore(99);
        studentScoreMapper.updateByPrimaryKeySelective(s);
        System.out.println(studentScoreMapper.selectByPrimaryKey(10015L));
    }

    @Override
    public Map<String, Object> runPythonFile4CU(String script, String code, String charset) {
        Map<String, Object> rtnMap = RunPythonUtil.runPythonFileByRuntime4CU(null,code,script, null,charset);
        if (rtnMap.get("result")==null) {
            rtnMap.put("status","fail");
        } else {
            rtnMap.put("status","success");
        }
        return rtnMap;
    }
}

package com.javatest.webservice.server.impl;

import com.javatest.po.StudentScore;
import com.javatest.service.StudentScoreService;
import com.javatest.webservice.server.CXFTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * webservice服务端实现类。注意，如果使用面向接口，那么在此实现类中必须通过endpointInterface指定服务端接口类
 */
@WebService(targetNamespace = "http://impl.server.webservice.javatest.com/",    // 命名空间一般为包名的倒置
        endpointInterface = "com.javatest.webservice.server.CXFTestService"
)
@Service
public class CXFTestServiceImpl implements CXFTestService {

    @Autowired
    private StudentScoreService service;

    @Override
    public StudentScore getStudentScore(long id) {
        return service.selectByPrimaryKey(id);
    }
}

package com.javatest.webservice.server.impl;

import com.javatest.po.StudentScore;
import com.javatest.service.StudentScoreService;
import com.javatest.webservice.server.JAXTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * webservice服务端实现类。注意，如果使用面向接口，那么在此实现类中必须通过endpointInterface指定服务端接口类
 */
@WebService(serviceName = "JAXTest",targetNamespace = "impl.server.webservice.javatest.com",endpointInterface = "com.javatest.webservice.server.JAXTestService")
@Service
public class JAXTestServiceImpl implements JAXTestService {

    @Autowired
    private StudentScoreService service;

    @Override
    public StudentScore getStudentScore(long id) {
        return service.selectByPrimaryKey(id);
    }

    // 发布服务
    public static void main(String[] args) {
        // 指定服务url
            String url = "http://localhost:9010/javatest/webservice";
        // 指定服务实现类
        JAXTestService server = new JAXTestServiceImpl();
        // 通过Endpoint发布服务
        Endpoint.publish(url, server);
    }
}

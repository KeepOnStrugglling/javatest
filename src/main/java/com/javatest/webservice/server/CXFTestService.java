package com.javatest.webservice.server;

import com.javatest.po.StudentScore;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * webservice服务端接口类
 */
@WebService
public interface CXFTestService {

    /**
     * 提供调用的方法，使用@WebMethod注释
     * @param id
     * @return StudentScore
     */
    @WebMethod(operationName = "getStudentScoreById")
    StudentScore getStudentScore(@WebParam(name = "id") long id);
}

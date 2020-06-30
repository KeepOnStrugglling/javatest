package com.javatest.service;

import com.javatest.po.StudentScore4JRS;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 使用JRS提供restful风格的接口
 */
@Path("/jrs/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface StudentScoreService4JRS {

    @POST                                   // 请求格式：有GET\POST\PUT\DELETE等
    @Produces(MediaType.APPLICATION_XML)    // 返回xml格式的数据
    @Consumes(MediaType.APPLICATION_JSON)   // 请求的格式规定为json
    @Path("/xml/findOne/")  // 指定请求的路径
    StudentScore4JRS selectByPrimaryKey(Long id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)   // 返回JSON格式的数据
    @Path("/json/findAll/")
    List<StudentScore4JRS> queryStudentScore(StudentScore4JRS studentScore);
}

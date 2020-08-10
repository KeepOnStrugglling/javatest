// 注意，本类不应出现package关键字，所以该类需要去掉package之后保存为java文件，用原始的cmd语句编译为class文件
package com.javatest.webservice.axis2server.pojo;

/**
 * 使用pojo类实现axis部署webservice
 * 注意：pojo类不能依赖其他类
 */
public class PojoServer {
    public String testPojo(String name) {
        return name+"success";
    }
}
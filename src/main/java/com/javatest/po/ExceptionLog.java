package com.javatest.po;

import java.util.Date;
import java.io.Serializable;
import java.util.Date;

/**
 * (ExceptionLog)实体类
 *
 * @author hjw
 * @since 2020-04-17 14:55:58
 */
public class ExceptionLog implements Serializable {
    private static final long serialVersionUID = 628602235870716712L;
    //主键id
    private Integer id;
    //用户id
    private String userId;
    //用户名
    private String userName;
    //异常名称
    private String exceptionName;
    //异常描述
    private String exceptionMsg;
    //请求方法名
    private String methodName;
    //请求ip
    private String ip;
    //请求地址
    private String url;
    //请求参数
    private String requestParam;
    //异常产生时间
    private Date createTime;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim(); 
    }
        
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim(); 
    }
        
    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName == null ? null : exceptionName.trim(); 
    }
        
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg == null ? null : exceptionMsg.trim(); 
    }
        
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim(); 
    }
        
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim(); 
    }
        
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim(); 
    }
        
    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam == null ? null : requestParam.trim(); 
    }
        
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
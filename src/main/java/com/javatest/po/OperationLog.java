package com.javatest.po;

import java.util.Date;
import java.io.Serializable;
import java.util.Date;

/**
 * (OperationLog)实体类
 *
 * @author hjw
 * @since 2020-04-16 17:20:05
 */
public class OperationLog implements Serializable {
    private static final long serialVersionUID = -82171662545207602L;
    //主键id
    private Integer id;
    //用户id
    private String userId;
    //用户名
    private String userName;
    //请求类型
    private String type;
    //请求模块
    private String module;
    //请求描述
    private String requestDes;
    //请求方法名
    private String methodName;
    //请求ip
    private String ip;
    //请求地址
    private String url;
    //请求参数
    private String requestParam;
    //返回值
    private String returnData;
    //请求开始时间
    private Date startTime;
    //请求完成时间
    private Date finishTime;
    //接口返回时间
    private Date returnTime;

    
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
        
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim(); 
    }
        
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim(); 
    }
        
    public String getRequestDes() {
        return requestDes;
    }

    public void setRequestDes(String requestDes) {
        this.requestDes = requestDes == null ? null : requestDes.trim(); 
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
        
    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData == null ? null : returnData.trim(); 
    }
        
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
        
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
        
    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
    
}
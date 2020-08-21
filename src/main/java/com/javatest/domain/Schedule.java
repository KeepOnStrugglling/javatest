package com.javatest.domain;

import java.util.Date;
import java.io.Serializable;
import java.util.Date;

/**
 * (Schedule)实体类
 *
 * @author hjw
 * @since 2020-08-21 15:16:11
 */
public class Schedule implements Serializable {
    private static final long serialVersionUID = -44400632731736429L;
    //调度任务id
    private Integer id;
    //调度任务名
    private String scheduleName;
    //cron表达式
    private String cronExpression;
    //执行任务id
    private String taskId;
    //状态：0-启动，1-暂停，2-删除
    private Integer status;
    //调度开始时间
    private Date startTime;
    //调度结束时间
    private Date endTime;
    //下次执行调度时间
    private Date nextTime;
    //创建者id
    private String creatorId;
    //创建时间
    private Date creatorTime;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        
    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName == null ? null : scheduleName.trim(); 
    }
        
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim(); 
    }
        
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim(); 
    }
        
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
        
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
        
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
        
    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }
        
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim(); 
    }
        
    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }
    
}
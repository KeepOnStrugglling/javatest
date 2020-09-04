package com.javatest.schedule.quartz.projectSample.service;

import com.javatest.domain.Schedule;

import java.util.List;
import java.util.Map;

/**
 * @author azure
 * 调度任务管理类接口
 */
public interface QuartzManager {

    void initScheduler();

    void addJob(Schedule schedule);

    void addJob(String jobName, Class<? extends org.quartz.Job> jobClazz, String cronExpression, Map<String, Object> jobDataMap);

    void modifyJob(String jobName, String cronExpression, Map<String,Object> jobDataMap);

    void removeJob(String jobName);

    void resumeJob(String jobName);

    void pauseJob(String jobName);

    List<String> getAllJob();

    void shutdownSchedule();
}

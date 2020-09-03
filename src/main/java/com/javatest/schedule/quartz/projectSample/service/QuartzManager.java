package com.javatest.schedule.quartz.projectSample.service;

import com.javatest.domain.Schedule;

import java.util.List;

/**
 * @author azure
 * 调度任务管理类接口
 */
public interface QuartzManager {

    void initScheduler();

    void addJob(Schedule schedule);

    void modifyJob(Schedule schedule);

    void removeJob(Schedule schedule);

    void resumeJob(Schedule schedule);

    void pauseJob(Schedule schedule);

    List<String> getAllJob();

    void shutdownSchedule();
}

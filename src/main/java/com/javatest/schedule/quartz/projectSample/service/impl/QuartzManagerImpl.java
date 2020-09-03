package com.javatest.schedule.quartz.projectSample.service.impl;

import com.javatest.domain.Schedule;
import com.javatest.enums.ScheduleStatusEnum;
import com.javatest.schedule.quartz.projectSample.ScheduleJob;
import com.javatest.schedule.quartz.projectSample.service.QuartzManager;
import com.javatest.service.ScheduleService;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author azure
 * 创建管理类实现类，完成调度功能
 */
@Service
@Transactional
public class QuartzManagerImpl implements QuartzManager {

    private static final String JOB_GROUP_NAME = "DEFAULT_JOBGROUP_NAME";
    private static final String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGERGROUP_NAME";

    // 因为QuartzConfig已经注入了Scheduler，直接获取即可
    private final Scheduler scheduler;
    private final ScheduleService scheduleService;

    @Autowired
    public QuartzManagerImpl(Scheduler scheduler, ScheduleService scheduleService) {
        this.scheduler = scheduler;
        this.scheduleService = scheduleService;
    }

    /**
     * 启动调度，从数据库中获取全部调度任务
     */
    @Override
    public void initScheduler() {
        List<Schedule> schedules = scheduleService.getRunnableSchdules();
        for (Schedule schedule : schedules) {
            if (schedule.getStatus().equals(ScheduleStatusEnum.RUNNABLE.getStatus())) {
                this.addJob(schedule);
            }
        }
    }

    @Override
    public void addJob(Schedule schedule) {
        try {
            // 创建jobDetail
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                    .withIdentity(schedule.getTaskId(),JOB_GROUP_NAME)  // 用task的id作为jobKey，指定分组
                    .build();
            // 创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(schedule.getTaskId(),TRIGGER_GROUP_NAME)  // 用task的id作为jobKey，指定分组
                    .withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCronExpression()))   // 指定cron表达式
                    .startNow().build();

            scheduler.scheduleJob(jobDetail,trigger);
            // 启动调度
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyJob(Schedule schedule) {

    }

    @Override
    public void removeJob(Schedule schedule) {

    }

    @Override
    public void resumeJob(Schedule schedule) {

    }

    @Override
    public void pauseJob(Schedule schedule) {

    }

    @Override
    public List<String> getAllJob() {
        return null;
    }

    @Override
    public void shutdownSchedule() {

    }
}

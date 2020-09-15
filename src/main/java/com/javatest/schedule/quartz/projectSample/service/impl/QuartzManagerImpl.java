package com.javatest.schedule.quartz.projectSample.service.impl;

import com.javatest.domain.Schedule;
import com.javatest.enums.ScheduleStatusEnum;
import com.javatest.schedule.quartz.projectSample.ScheduleJob;
import com.javatest.schedule.quartz.projectSample.service.QuartzManager;
import com.javatest.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author azure
 * 创建管理类实现类，完成调度功能
 */
@Service
@Transactional
public class QuartzManagerImpl implements QuartzManager {

    private Logger log = LoggerFactory.getLogger(QuartzManagerImpl.class);

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
        log.info("正在初始化定时调度...");
        startAllJobs();

        List<Schedule> schedules = scheduleService.getRunnableSchdules();
        for (Schedule schedule : schedules) {
            if (schedule.getStatus().equals(ScheduleStatusEnum.RUNNABLE.getStatus())) {
                this.addJob(schedule);
            }
        }
        log.info("完成定时调度任务的初始化！");
    }

    /**
     * 根据Schedule实体类添加调度任务
     *  **注意：需要确保scheduler已经启动，或者添加任务后会马上启动，否则则需要scheduler.start();**
     * @param schedule schedule对象
     */
    @Override
    public void addJob(Schedule schedule) {
        // 确保initScheduler启动的调度任务符合时间有效性
        if (checkTime(schedule.getStartTime(),schedule.getEndTime())) {
            return;
        }
        try {
            // 创建jobDetail
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                    .withIdentity(schedule.getTaskId(),JOB_GROUP_NAME)  // 用task的id作为jobKey，指定分组
                    .build();
            // 创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(schedule.getTaskId(),TRIGGER_GROUP_NAME)  // 用task的id作为jobKey，指定分组
                    .withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCronExpression()))   // 指定cron表达式
                    .startAt(schedule.getStartTime())   // 指定开始时间
                    .endAt(schedule.getEndTime())   // 指定结束时间
                    .build();
            // 将调度任务添加到jobDataMap中
            trigger.getJobDataMap().put("schedule",schedule);
            scheduler.scheduleJob(jobDetail,trigger);

            log.info("新增定时任务：" + schedule.getTaskId());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查时间有效性
     */
    public static boolean checkTime(Date startTime, Date endTime) {
        if (startTime==null || endTime==null || startTime.getTime()>=endTime.getTime() || System.currentTimeMillis()>=endTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 根据cron表达式添加调度任务
     * @param jobName 任务名称
     * @param jobClazz 需要执行的job类，也就是Job接口的实现类
     * @param cronExpression cron表达式
     * @param jobDataMap 参数集合，注意，里面要包含schedule
     */
    @Override
    public void addJob(String jobName, Class<? extends org.quartz.Job> jobClazz, String cronExpression, Map<String, Object> jobDataMap) {
        try {
            // 获取参数
            Schedule schedule = (Schedule) jobDataMap.get("schedule");
            // 创建jobDetail
            JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                    .withIdentity(jobName,JOB_GROUP_NAME)
                    .build();
            // 创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName,TRIGGER_GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))   // 指定cron表达式
                    .startAt(schedule.getStartTime())
                    .endAt(schedule.getEndTime())
                    .build();
            // 添加参数
            trigger.getJobDataMap().putAll(jobDataMap);

            scheduler.scheduleJob(jobDetail,trigger);
            // 启动调度
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
            log.info("新增定时任务：" + jobName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新调度任务，可以更新调度频率或参数
     * @param jobName 任务名称
     * @param cronExpression cron表达式
     * @param jobDataMap 参数集合，注意，里面要包含schedule
     */
    @Override
    public void modifyJob(String jobName, String cronExpression, Map<String,Object> jobDataMap) {
        try {
            // 获取triggerkey
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName,TRIGGER_GROUP_NAME);

            // 获取原来的trigger,这里不能用多态，必须强转为CronTrigger，否则在下面调用getTriggerBuilder时无法确定是返回哪种TriggerBuilder
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 获取参数
            Schedule schedule = (Schedule) jobDataMap.get("schedule");

            // 根据新的cron表达式构建新的trigger
            if (StringUtils.isNotBlank(cronExpression)) {
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .startAt(schedule.getStartTime())
                        .endAt(schedule.getEndTime())
                        .build();
            }

            // 更新参数
            trigger.getJobDataMap().putAll(jobDataMap);

            // 用新的trigger执行job
            scheduler.rescheduleJob(triggerKey,trigger);
            log.info("定时调度任务：" + jobName + " 已更新");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据任务名称删除调度任务
     * @param jobName
     */
    @Override
    public void removeJob(String jobName) {
        try {
            // 暂停
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
            // 停止调度
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, JOB_GROUP_NAME));
            log.info("删除调度任务：" + jobName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复一个调度任务
     * @param jobName
     */
    @Override
    public void resumeJob(String jobName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName,JOB_GROUP_NAME));
            log.info("恢复调度任务：" + jobName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停一个调度任务
     * @param jobName
     */
    @Override
    public void pauseJob(String jobName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName,JOB_GROUP_NAME));
            log.info("暂停调度任务：" + jobName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全部调度任务的任务名称
     */
    @Override
    public List<String> getAllJob() {
        List<String> jobNameList = new ArrayList<>();
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
            for (JobKey jobKey : jobKeys) {
                jobNameList.add(jobKey.getName());
                log.info("调度任务：" + jobKey.getName());
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobNameList;
    }

    /**
     * 开启调度功能
     */
    public void startAllJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭调度功能
     */
    @Override
    public void shutdownSchedule() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
                log.info("已关闭调度功能");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}

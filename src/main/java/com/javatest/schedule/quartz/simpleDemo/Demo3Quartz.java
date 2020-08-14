package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * 获取JobExecutionContext的JobDataMap存取数据
 */
public class Demo3Quartz {
    public static void main(String[] args) throws SchedulerException {

        Scheduler sched = new StdSchedulerFactory().getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(Demo3Job.class)
                .withIdentity("demoJob","group1")
                // 第一种方式：在创建JobDetail时存入数据
                .usingJobData("param1","hello")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("demotrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .usingJobData("param1","world")
                .build();

        // 测试并集中重复的key是否会覆盖前值
        // 发现并集的值没有被覆盖，仍未trigger的值
//        jobDetail.getJobDataMap().put("param1","hello");

        // 发现并集的值被覆盖，为trigger的新值
        trigger.getJobDataMap().put("param1","hello");

        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}

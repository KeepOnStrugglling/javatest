package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * JobDataMap存取数据
 */
public class Demo2Quartz {
    public static void main(String[] args) throws SchedulerException {

        Scheduler sched = new StdSchedulerFactory().getScheduler();

        // 第二种方式，先创建JobDataMap，存入数据后再放入JobDetail
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.put("param1","hello");
//        jobDataMap.put("param2","world");

        JobDetail jobDetail = JobBuilder.newJob(Demo2Job.class)
                .withIdentity("demoJob","group1")
                // 第一种方式：在创建JobDetail时存入数据
//                .usingJobData("param1","hello")
//                .usingJobData("param2","world")
                // 第二种方式：再创建好JobDataMap后放入JobDetail
//                .usingJobData(jobDataMap)
                .build();

        // 第三种方式：先创建JobDetail，获取其JobDataMap，再存入数据
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("param1","hello");
        // 或直接存入
        jobDetail.getJobDataMap().put("param2","world");

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("demotrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();

        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}

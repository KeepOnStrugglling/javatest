package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @author azure
 * 最简单的Scheduler实例实现调度
 */
public class DemoQuartz {
    public static void main(String[] args) throws SchedulerException {

        //1. 创建Scheduler
        Scheduler sched = new StdSchedulerFactory().getScheduler();

        //2. 创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(DemoJob.class)
                .withIdentity("demoJob","group1")     //给Job实例命名和分组
                .build();

        //3. 创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("demotrigger", "group1")    //给Trigger实例命名和分组
                .startNow()     // 启动后立马执行调度
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)      // 每隔10s触发一次任务
                        .repeatForever())
                .build();

        //4. 给Scheduler注入Job实例和Trigger实例
        sched.scheduleJob(jobDetail,trigger);

        //5. 启动Scheduler
        sched.start();
    }
}

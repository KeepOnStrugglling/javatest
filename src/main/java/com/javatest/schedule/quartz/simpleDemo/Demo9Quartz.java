package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.calendar.DailyCalendar;

import java.text.ParseException;
import java.util.Calendar;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * CronCalendar:cron表达式，默认是排除
 */
public class Demo9Quartz {
    public static void main(String[] args) throws SchedulerException {
        Scheduler sched = new StdSchedulerFactory().getScheduler();

        // 创建Calendar对象
        try {
            // 默认是排除的，比如排除工作时间
            CronCalendar cronCalendar = new CronCalendar("* * 0-7,18-23 ? * *");
            // 加入到容器中
            sched.addCalendar("cronCalendar",cronCalendar,true,true);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JobDetail jobDetail = JobBuilder.newJob(DemoJob.class)
                .withIdentity("demoJob","group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("demotrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                // 指定Calendar，参数为上面addCalendar方法的参数1
                .modifiedByCalendar("cronCalendar")
                .build();


        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}
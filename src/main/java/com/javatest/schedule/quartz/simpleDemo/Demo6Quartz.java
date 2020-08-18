package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.calendar.MonthlyCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * MonthlyCalendar:排除每月指定哪几号不触发
 */
public class Demo6Quartz {
    public static void main(String[] args) throws SchedulerException {

        Scheduler sched = new StdSchedulerFactory().getScheduler();

        // 创建Calendar对象
        MonthlyCalendar monthlyCalendar = new MonthlyCalendar();
        // 设置排除日期
        monthlyCalendar.setDayExcluded(8,true);
        monthlyCalendar.setDayExcluded(18,true);
        monthlyCalendar.setDayExcluded(28,true);
        // 加入到容器中
        // 参数1为对象名，用作唯一标识，参数2为Calendar对象，参数3为是否替换原来的同名对象，参数4为是否替换原来的trigger
        sched.addCalendar("monthlyCalendar",monthlyCalendar,true,true);

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
                .modifiedByCalendar("monthlyCalendar")
                .build();


        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}

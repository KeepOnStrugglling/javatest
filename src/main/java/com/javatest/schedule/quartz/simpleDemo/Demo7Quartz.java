package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.WeeklyCalendar;

import java.util.Calendar;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * WeeklyCalendar:排除周几不触发
 */
public class Demo7Quartz {
    public static void main(String[] args) throws SchedulerException {

        Scheduler sched = new StdSchedulerFactory().getScheduler();

        // 创建Calendar对象
        WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
        // 设置排除日期
        weeklyCalendar.setDayExcluded(Calendar.TUESDAY,true);
        weeklyCalendar.setDayExcluded(Calendar.THURSDAY,true);

        // 周六和周日默认被排除，如果要不排除，则需要手动设置
        // 比如添加周六
        weeklyCalendar.setDayExcluded(Calendar.SATURDAY, false);

        // 加入到容器中
        // 参数1为对象名，用作唯一标识，参数2为Calendar对象，参数3为是否替换原来的同名对象，参数4为是否替换原来的trigger
        sched.addCalendar("weeklyCalendar",weeklyCalendar,true,true);

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
                .modifiedByCalendar("weeklyCalendar")
                .build();


        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}
package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.calendar.WeeklyCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * DailyCalendar:一天中的某个时间范围进行排除/触发
 */
public class Demo8Quartz {
    public static void main(String[] args) throws SchedulerException {
        Scheduler sched = new StdSchedulerFactory().getScheduler();
        // 时间范围的开始时间
        Calendar start = Calendar.getInstance();
        start.setTime(DateBuilder.dateOf(13,0,0));
        // 时间范围的结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(DateBuilder.dateOf(14,0,0));

        // 创建Calendar对象
//        DailyCalendar dailyCalendar = new DailyCalendar(start,end);
        // 另外也可以直接用字符串来构造DailyCalendar
        DailyCalendar dailyCalendar = new DailyCalendar("13:00:00","14:30:15");

        // 设置是触发还是排除,true为范围内触发，false为范围内不触发
        dailyCalendar.setInvertTimeRange(false);

        // 加入到容器中
        // 参数1为对象名，用作唯一标识，参数2为Calendar对象，参数3为是否替换原来的同名对象，参数4为是否替换原来的trigger
        sched.addCalendar("dailyCalendar",dailyCalendar,true,true);

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
                .modifiedByCalendar("dailyCalendar")
                .build();


        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}
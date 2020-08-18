package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * HolidayCalendar
 */
public class Demo5Quartz {
    public static void main(String[] args) throws SchedulerException {

        Scheduler sched = new StdSchedulerFactory().getScheduler();

        // 创建HolidayCalendar对象
        HolidayCalendar holidayCalendar = new HolidayCalendar();
        // 设置指定的日期
        // 注意！！下面的构建Calendar方式中的第二个参数（也就是month）是从0开始！！也就是0为1月，11为12月！
        Calendar calendar1 = new GregorianCalendar(2020,7,18);
        Calendar calendar2 = new GregorianCalendar(2020,9,1);
        Calendar calendar3 = new GregorianCalendar(2021,0,1);
        // 调用addExcludedDate方法设置排除日期
        holidayCalendar.addExcludedDate(calendar1.getTime());
        holidayCalendar.addExcludedDate(calendar2.getTime());
        holidayCalendar.addExcludedDate(calendar3.getTime());
        // 将HolidayCalendar对象加入到容器中
        // 参数1为对象名，用作唯一标识，参数2为Calendar对象，参数3为是否替换原来的同名对象，参数4为是否替换原来的trigger
        sched.addCalendar("holidays",holidayCalendar,true,true);

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
                .modifiedByCalendar("holidays")
                .build();


        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}

package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * calendar
 */
public class Demo4Quartz {
    public static void main(String[] args) throws SchedulerException {

        Scheduler sched = new StdSchedulerFactory().getScheduler();

        // 创建AnnualCalendar对象
        AnnualCalendar annualCalendar = new AnnualCalendar();
        Calendar calendar = GregorianCalendar.getInstance();
        // 设置指定的日期（比如6月30日），注意，使用Calendar的月份枚举时需要+1！，也可以直接写数字
        calendar.set(Calendar.MONTH,Calendar.JUNE+1);
        calendar.set(Calendar.DATE,30);
        // 设置是排除还是包含,false为排除，true为包含
        annualCalendar.setDayExcluded(calendar,true);
        // 将AnnualCalendar对象加入到容器中
        // 参数1为对象名，用作唯一标识，参数2为Calendar对象，参数3为是否替换原来的同名对象，参数4为是否替换原来的trigger
        sched.addCalendar("annualCalendar",annualCalendar,true,true);

        JobDetail jobDetail = JobBuilder.newJob(Demo3Job.class)
                .withIdentity("demoJob","group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("demotrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                // 指定Calendar，参数为上面addCalendar方法的参数1
                .modifiedByCalendar("annualCalendar")
                .build();


        sched.scheduleJob(jobDetail,trigger);
        sched.start();
    }
}

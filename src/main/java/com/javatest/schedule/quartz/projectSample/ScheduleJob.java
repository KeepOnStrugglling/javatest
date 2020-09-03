package com.javatest.schedule.quartz.projectSample;

import com.javatest.config.SpringContextUtil;
import com.javatest.domain.Schedule;
import com.javatest.service.ScheduleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author azure
 * 任务job类
 */
public class ScheduleJob implements Job {

    @Autowired
    private ScheduleService service;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取Spring容器中的bean
//        getBeanFromSpringContext();
        // 获取调度信息
        Schedule schedule = (Schedule) context.getMergedJobDataMap().get("Schedule");
        // 通过schedule对象获取相应需要执行的任务，并执行。这里就只是输出taskId模拟任务执行
        System.out.println("执行任务：" + schedule.getTaskId());

        // 我们可以把下一次执行的时间保存起来，这步操作视需求而定，非必须
        Date nextTime = context.getNextFireTime();
        schedule.setNextTime(nextTime);
        service.updateByPrimaryKey(schedule);
    }

//    private void getBeanFromSpringContext() {
//        // Spring容器的bean的名称是小写开头，实际上是获取ScheduleService在Spring容器里的bean
//        this.service = (ScheduleService) SpringContextUtil.getApplicationContext().getBean("scheduleService");
//    }
}

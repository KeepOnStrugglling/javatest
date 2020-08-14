package com.javatest.schedule.quartz.simpleDemo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Demo3Job implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("执行调度任务...");
        System.err.println(context.getJobDetail().getKey());
        System.err.println(context.getTrigger().getKey());
        // 取出JobDataMap的数据
        System.out.println(context.getJobDetail().getJobDataMap().get("param1"));
        System.out.println(context.getTrigger().getJobDataMap().get("param1"));
        // 取出JobExecutionContext合并后的JobDataMap数据
        System.out.println(context.getMergedJobDataMap().get("param1"));
    }
}

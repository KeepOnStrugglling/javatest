package com.javatest.config;

import com.javatest.schedule.quartz.projectSample.service.QuartzManager;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author azure
 * 用于延时初始化调度任务
 */
@Component
public class QuartzJobInitExecutor {

    private final QuartzManager quartzManager;

    @Autowired
    public QuartzJobInitExecutor(QuartzManager quartzManager) {
        this.quartzManager = quartzManager;
    }

    public void runnable(){
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("DemoJob-rate-%d").daemon(false).build());
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                quartzManager.initScheduler();
            }
        }, 7, TimeUnit.SECONDS);
    }
}

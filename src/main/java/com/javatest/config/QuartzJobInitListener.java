package com.javatest.config;

import com.javatest.schedule.quartz.projectSample.service.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobInitListener implements CommandLineRunner {

    private final QuartzManager quartzManager;

    @Autowired
    public QuartzJobInitListener(QuartzManager quartzManager) {
        this.quartzManager = quartzManager;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("任务调度开启中...");
        quartzManager.initScheduler();
        System.out.println("任务调度已经启动");
    }
}

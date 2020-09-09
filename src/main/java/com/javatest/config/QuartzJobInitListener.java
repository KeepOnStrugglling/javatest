package com.javatest.config;

import com.javatest.schedule.quartz.projectSample.service.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class QuartzJobInitListener implements CommandLineRunner {

    private final QuartzManager quartzManager;

    @Autowired
    public QuartzJobInitListener(QuartzManager quartzManager) {
        this.quartzManager = quartzManager;
    }

    @Autowired
    private QuartzJobInitExecutor executor;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("任务调度开启中...");
        // 方法1：线程暂停实现延迟执行调度任务初始化的方法
//        Thread.sleep(15000);
//        quartzManager.initScheduler();

        // 方法2：使用一次性任务延迟执行调度任务初始化方法
        executor.runnable();
        System.out.println("任务调度已经启动");
    }
}

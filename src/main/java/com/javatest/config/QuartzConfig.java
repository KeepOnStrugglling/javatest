package com.javatest.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author azure
 * @since 2020-08-21 15:16:11
 * @desc quartz配置类
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);     // 覆盖已存在的调度任务
        factoryBean.setStartupDelay(30);    // 设置延迟启动调度的时间，避免系统启动未完全就进行调度
        return factoryBean;
    }

    @Bean(name = "scheduler")
    public Scheduler scheduler(){
        return schedulerFactoryBean().getScheduler();
    }
}

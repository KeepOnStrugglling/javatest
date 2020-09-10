package com.javatest.config;

import com.javatest.schedule.quartz.projectSample.service.ScheduleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 用于定时刷新调度任务：实现延迟开启调度，定时刷新调度
 * 这时候就不需要QuartzJobInitListener，QuartzConfig也不用setStartupDelay
 */
@Component
public class QuartzJobInit {

    private final ScheduleManageService manageService;

    @Autowired
    public QuartzJobInit(ScheduleManageService manageService) {
        this.manageService = manageService;
    }

    // 项目启动后延迟10s启动调度，每隔1小时刷新调度任务
    @Scheduled(initialDelay = 10000, fixedDelay = 3600000)
    public void scheduleInit(){
        manageService.refreshSchedules();
    }
}

package com.javatest.schedule.quartz.projectSample.service.impl;

import com.javatest.domain.Schedule;
import com.javatest.schedule.quartz.projectSample.ScheduleJob;
import com.javatest.schedule.quartz.projectSample.service.QuartzManager;
import com.javatest.schedule.quartz.projectSample.service.ScheduleManageService;
import com.javatest.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author azure
 * 业务管理类，提供复杂的调度功能
 */
@Service
@Transactional
public class ScheduleManageServiceImpl implements ScheduleManageService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final QuartzManager quartzManager;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleManageServiceImpl(QuartzManager quartzManager, ScheduleService scheduleService) {
        this.quartzManager = quartzManager;
        this.scheduleService = scheduleService;
    }

    /**
     * 刷新定时任务
     */
    @Override
    public void refreshSchedules() {
        Map<String, Object> jobDataMap = new HashMap();
        // 记录调度中的任务名
        List<String> scheduledJobNames = new ArrayList<>();
        // 获取数据库中的数据
        List<Schedule> schedules = scheduleService.getRunnableSchdules();
        // 获取调度中的任务
        List<String> jobNames = quartzManager.getAllJob();

        // 1. 判断数据库中是否有有效的调度任务
        if (schedules == null || schedules.size() == 0) {
            log.info("系统在当前时间没有有效的调度任务！");
            // 清除调度容器的任务
            for (String jobName : jobNames) {
                quartzManager.removeJob(jobName);
            }
            return;
        }
        // 2. 同步定时任务
        for (Schedule schedule : schedules) {
            // 2.1 判断时间有效性
            if (QuartzManagerImpl.checkTime(schedule.getStartTime(),schedule.getEndTime())) {
                log.warn(String.format("调度任务：%s 的开始时间和结束时间不符合要求，请检查数据",schedule.getTaskId()));
                continue;
            }
            // 2.2 刷新任务
            if (StringUtils.isNotBlank(schedule.getCronExpression())){
                scheduledJobNames.add(schedule.getTaskId());
                jobDataMap.put("schedule",schedule);

                if (jobNames.contains(schedule.getTaskId())) {
                    quartzManager.modifyJob(schedule.getTaskId(),schedule.getCronExpression(),jobDataMap);
                } else {
                    // 这里用addJob(Schedule schedule)也没问题
                    quartzManager.addJob(schedule.getTaskId(), ScheduleJob.class, schedule.getCronExpression(),jobDataMap);
                }
            } else {
                log.warn(String.format("调度任务：%s 的cron表达式为空，请检查数据",schedule.getTaskId()));
            }
        }
        // 3. 清除多余的调度任务
        for (String jobName : jobNames) {
            if (!scheduledJobNames.contains(jobName)) {
                quartzManager.removeJob(jobName);
            }
        }
    }
}

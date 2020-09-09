package com.javatest.controller;

import com.javatest.domain.Schedule;
import com.javatest.enums.ScheduleStatusEnum;
import com.javatest.schedule.quartz.projectSample.service.QuartzManager;
import com.javatest.schedule.quartz.projectSample.service.ScheduleManageService;
import com.javatest.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleManageService manageService;

    @PostMapping("/addSchedule")
    public void addSchedule(@RequestBody Schedule schedule) {
        scheduleService.insertSelective(schedule);
        quartzManager.addJob(schedule);
    }

    @PutMapping("/modifySchedule")
    public void modifySchedule(@RequestBody Schedule schedule) {
        scheduleService.updateByPrimaryKeySelective(schedule);
        Map<String,Object> map = new HashMap<>();
        map.put("schedule",schedule);
        quartzManager.modifyJob(schedule.getTaskId(),schedule.getCronExpression(),map);
    }

    @PostMapping("/pauseSchedule")
    public void pauseSchedule(int id) {
        Schedule schedule = scheduleService.selectByPrimaryKey(id);
        schedule.setStatus(ScheduleStatusEnum.PAUSE.getStatus());
        scheduleService.updateByPrimaryKeySelective(schedule);
        quartzManager.pauseJob(schedule.getTaskId());
    }

    @PostMapping("/resumeSchedule")
    public void resumeSchedule(int id) {
        Schedule schedule = scheduleService.selectByPrimaryKey(id);
        schedule.setStatus(ScheduleStatusEnum.RUNNABLE.getStatus());
        scheduleService.updateByPrimaryKeySelective(schedule);
        quartzManager.resumeJob(schedule.getTaskId());
    }

    @PostMapping("/delSchedule")
    public void delSchedule(int id) {
        Schedule schedule = scheduleService.selectByPrimaryKey(id);
        quartzManager.removeJob(schedule.getTaskId());
        scheduleService.deleteByPrimaryKey(id);
    }

    @GetMapping("/shutDownSchedule")
    public void shutDownSchedule() {
        quartzManager.shutdownSchedule();
    }

    @GetMapping("/getAllJob")
    public void getAllJob() {
        quartzManager.getAllJob();
    }

    @GetMapping("/refreshAllJob")
    public void refreshAllJob() {
        manageService.refreshSchedules();
    }
}

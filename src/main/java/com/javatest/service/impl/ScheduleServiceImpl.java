package com.javatest.service.impl;

import com.javatest.domain.Schedule;
import com.javatest.mapper.ScheduleMapper;
import com.javatest.service.ScheduleService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Date;

/**
 * (Schedule)表服务实现类
 *
 * @author azure
 * @since 2020-09-01 09:37:15
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    /**
     * 通过主键查询单条数据
     * @param id
     * @return Schedule
     */
    @Override
    public Schedule selectByPrimaryKey(Integer id) {
        // TODO Auto-generated method
        return scheduleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询列表(不分页)
     * @param schedule 条件
     * @return 对象列表
     */
    @Override
    public List<Schedule> queryScheduleList(Schedule schedule) {
        // TODO Auto-generated method
        return scheduleMapper.queryScheduleList(schedule);
    }

    /**
     * 新增数据(全值覆盖)
     * @param schedule 实例对象
     * @return
     */
    @Override
    public int insert(Schedule schedule) {
        // TODO Auto-generated method
        return scheduleMapper.insert(schedule);
    }
    
    /**
     * 新增数据(有值赋值)
     * @param schedule 实例对象
     * @return
     */
    @Override
    public int insertSelective(Schedule schedule) {
        // TODO Auto-generated method
        return scheduleMapper.insertSelective(schedule);
    }

    /**
     * 修改数据(全值覆盖)
     * @param schedule 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKey(Schedule schedule) {
        // TODO Auto-generated method
        return scheduleMapper.updateByPrimaryKey(schedule);
    }
    
    /**
     * 修改数据(有值赋值)
     * @param schedule 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKeySelective(Schedule schedule) {
        // TODO Auto-generated method
        return scheduleMapper.updateByPrimaryKeySelective(schedule);
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        // TODO Auto-generated method
        return scheduleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Schedule> getRunnableSchdules() {
        return scheduleMapper.getRunnableSchdules();
    }
}
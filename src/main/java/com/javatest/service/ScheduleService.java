package com.javatest.service;

import com.javatest.domain.Schedule;
import java.util.List;

/**
 * (Schedule)表服务接口
 *
 * @author azure
 * @since 2020-09-01 09:37:15
 */
public interface ScheduleService {

    Schedule selectByPrimaryKey(Integer id);

    List<Schedule> queryScheduleList(Schedule schedule);

    int insert(Schedule schedule);
    
    int insertSelective(Schedule schedule);
    
    int updateByPrimaryKey(Schedule schedule);

    int updateByPrimaryKeySelective(Schedule schedule);

    int deleteByPrimaryKey(Integer id);

    List<Schedule> getRunnableSchdules();
}
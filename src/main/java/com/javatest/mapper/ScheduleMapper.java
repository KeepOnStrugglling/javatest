package com.javatest.mapper;

import com.javatest.domain.Schedule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * (Schedule)表数据库访问层
 *
 * @author azure
 * @since 2020-08-21 15:16:11
 */
@Repository
public interface ScheduleMapper {

    Schedule selectByPrimaryKey(Integer id);

    List<Schedule> queryScheduleList(Schedule schedule);

    int insert(Schedule schedule);
    
    int insertSelective(Schedule schedule);
    
    int updateByPrimaryKey(Schedule schedule);

    int updateByPrimaryKeySelective(Schedule schedule);

    int deleteByPrimaryKey(Integer id);

    @Select("select * from studentscore.schedule where status=0")
    List<Schedule> getRunnableSchdules();
}
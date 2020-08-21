package com.javatest.mapper;

import com.javatest.domain.ExceptionLog;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * (ExceptionLog)表数据库访问层
 *
 * @author hjw
 * @since 2020-04-17 14:55:58
 */
@Repository
public interface ExceptionLogMapper {

    ExceptionLog selectByPrimaryKey(Integer id);

    List<ExceptionLog> queryExceptionLogList(ExceptionLog exceptionLog);

    int insert(ExceptionLog exceptionLog);
    
    int insertSelective(ExceptionLog exceptionLog);
    
    int updateByPrimaryKey(ExceptionLog exceptionLog);

    int updateByPrimaryKeySelective(ExceptionLog exceptionLog);

    int deleteByPrimaryKey(Integer id);

}
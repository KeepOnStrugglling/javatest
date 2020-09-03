package com.javatest.service;

import com.javatest.domain.ExceptionLog;

import java.util.List;

/**
 * (ExceptionLog)表服务接口
 *
 * @author azure
 * @since 2020-04-16 17:21:59
 */
public interface ExceptionLogService {

    ExceptionLog selectByPrimaryKey(Integer id);

    List<ExceptionLog> queryExceptionLogList(ExceptionLog exceptionLog);

    int insert(ExceptionLog exceptionLog);
    
    int insertSelective(ExceptionLog exceptionLog);
    
    int updateByPrimaryKey(ExceptionLog exceptionLog);

    int updateByPrimaryKeySelective(ExceptionLog exceptionLog);

    int deleteByPrimaryKey(Integer id);

}
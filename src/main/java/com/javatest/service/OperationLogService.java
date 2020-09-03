package com.javatest.service;

import com.javatest.domain.OperationLog;
import java.util.List;

/**
 * (OperationLog)表服务接口
 *
 * @author azure
 * @since 2020-04-16 17:20:13
 */
public interface OperationLogService {

    OperationLog selectByPrimaryKey(Integer id);

    List<OperationLog> queryOperationLogList(OperationLog operationLog);

    int insert(OperationLog operationLog);
    
    int insertSelective(OperationLog operationLog);
    
    int updateByPrimaryKey(OperationLog operationLog);

    int updateByPrimaryKeySelective(OperationLog operationLog);

    int deleteByPrimaryKey(Integer id);

}
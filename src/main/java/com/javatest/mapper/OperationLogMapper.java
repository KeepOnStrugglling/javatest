package com.javatest.mapper;

import com.javatest.domain.OperationLog;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * (OperationLog)表数据库访问层
 *
 * @author hjw
 * @since 2020-04-16 17:20:05
 */
@Repository
public interface OperationLogMapper {

    OperationLog selectByPrimaryKey(Integer id);

    List<OperationLog> queryOperationLogList(OperationLog operationLog);

    int insert(OperationLog operationLog);
    
    int insertSelective(OperationLog operationLog);
    
    int updateByPrimaryKey(OperationLog operationLog);

    int updateByPrimaryKeySelective(OperationLog operationLog);

    int deleteByPrimaryKey(Integer id);

}
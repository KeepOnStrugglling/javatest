package com.javatest.service.impl;

import com.javatest.po.OperationLog;
import com.javatest.dao.OperationLogMapper;
import com.javatest.service.OperationLogService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Date;

/**
 * (OperationLog)表服务实现类
 *
 * @author hjw
 * @since 2020-04-16 17:20:13
 */
@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    /**
     * 通过主键查询单条数据
     * @param id
     * @return OperationLog
     */
    @Override
    public OperationLog selectByPrimaryKey(Integer id) {
        return operationLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询列表(不分页)
     * @param operationLog 条件
     * @return 对象列表
     */
    @Override
    public List<OperationLog> queryOperationLogList(OperationLog operationLog) {
        return operationLogMapper.queryOperationLogList(operationLog);
    }

    /**
     * 新增数据(全值覆盖)
     * @param operationLog 实例对象
     * @return
     */
    @Override
    public int insert(OperationLog operationLog) {
        return operationLogMapper.insert(operationLog);
    }
    
    /**
     * 新增数据(有值赋值)
     * @param operationLog 实例对象
     * @return
     */
    @Override
    public int insertSelective(OperationLog operationLog) {
        return operationLogMapper.insertSelective(operationLog);
    }

    /**
     * 修改数据(全值覆盖)
     * @param operationLog 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKey(OperationLog operationLog) {
        return operationLogMapper.updateByPrimaryKey(operationLog);
    }
    
    /**
     * 修改数据(有值赋值)
     * @param operationLog 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKeySelective(OperationLog operationLog) {
        return operationLogMapper.updateByPrimaryKeySelective(operationLog);
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return operationLogMapper.deleteByPrimaryKey(id);
    }
}
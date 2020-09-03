package com.javatest.service.impl;

import com.javatest.mapper.ExceptionLogMapper;
import com.javatest.domain.ExceptionLog;
import com.javatest.service.ExceptionLogService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * (ExceptionLog)表服务实现类
 *
 * @author azure
 * @since 2020-04-16 17:21:59
 */
@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements ExceptionLogService {

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    /**
     * 通过主键查询单条数据
     * @param id
     * @return ExceptionLog
     */
    @Override
    public ExceptionLog selectByPrimaryKey(Integer id) {
        return exceptionLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询列表(不分页)
     * @param exceptionLog 条件
     * @return 对象列表
     */
    @Override
    public List<ExceptionLog> queryExceptionLogList(ExceptionLog exceptionLog) {
        return exceptionLogMapper.queryExceptionLogList(exceptionLog);
    }

    /**
     * 新增数据(全值覆盖)
     * @param exceptionLog 实例对象
     * @return
     */
    @Override
    public int insert(ExceptionLog exceptionLog) {
        return exceptionLogMapper.insert(exceptionLog);
    }
    
    /**
     * 新增数据(有值赋值)
     * @param exceptionLog 实例对象
     * @return
     */
    @Override
    public int insertSelective(ExceptionLog exceptionLog) {
        return exceptionLogMapper.insertSelective(exceptionLog);
    }

    /**
     * 修改数据(全值覆盖)
     * @param exceptionLog 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKey(ExceptionLog exceptionLog) {
        return exceptionLogMapper.updateByPrimaryKey(exceptionLog);
    }
    
    /**
     * 修改数据(有值赋值)
     * @param exceptionLog 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKeySelective(ExceptionLog exceptionLog) {
        return exceptionLogMapper.updateByPrimaryKeySelective(exceptionLog);
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return exceptionLogMapper.deleteByPrimaryKey(id);
    }
}
package com.javatest.service.impl;

import com.javatest.dao.ExceptionLogMapper;
import com.javatest.po.ExceptionLog;
import com.javatest.service.ExceptionLogService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * (ExceptionLog)表服务实现类
 *
 * @author hjw
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
        // TODO Auto-generated method
        return exceptionLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询列表(不分页)
     * @param exceptionLog 条件
     * @return 对象列表
     */
    @Override
    public List<ExceptionLog> queryExceptionLogList(ExceptionLog exceptionLog) {
        // TODO Auto-generated method
        return exceptionLogMapper.queryExceptionLogList(exceptionLog);
    }

    /**
     * 新增数据(全值覆盖)
     * @param exceptionLog 实例对象
     * @return
     */
    @Override
    public int insert(ExceptionLog exceptionLog) {
        // TODO Auto-generated method
        return exceptionLogMapper.insert(exceptionLog);
    }
    
    /**
     * 新增数据(有值赋值)
     * @param exceptionLog 实例对象
     * @return
     */
    @Override
    public int insertSelective(ExceptionLog exceptionLog) {
        // TODO Auto-generated method
        return exceptionLogMapper.insertSelective(exceptionLog);
    }

    /**
     * 修改数据(全值覆盖)
     * @param exceptionLog 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKey(ExceptionLog exceptionLog) {
        // TODO Auto-generated method
        return exceptionLogMapper.updateByPrimaryKey(exceptionLog);
    }
    
    /**
     * 修改数据(有值赋值)
     * @param exceptionLog 实例对象
     * @return 
     */
    @Override
    public int updateByPrimaryKeySelective(ExceptionLog exceptionLog) {
        // TODO Auto-generated method
        return exceptionLogMapper.updateByPrimaryKeySelective(exceptionLog);
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer id) {
        // TODO Auto-generated method
        return exceptionLogMapper.deleteByPrimaryKey(id);
    }
}
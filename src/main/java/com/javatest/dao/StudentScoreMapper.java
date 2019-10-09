package com.javatest.dao;

import com.javatest.po.StudentScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * (StudentScore)表数据库访问层
 *
 * @author makejava
 * @since 2019-09-30 11:07:00
 */
@Repository
public interface StudentScoreMapper {

    StudentScore selectByPrimaryKey(Long id);

    List<StudentScore> queryStudentScore(StudentScore studentScore);

    int insert(StudentScore studentScore);
    
    int insertSelective(StudentScore studentScore);
    
    int updateByPrimaryKey(StudentScore studentScore);

    int updateByPrimaryKeySelective(StudentScore studentScore);

    int deleteByPrimaryKey(Long id);

}
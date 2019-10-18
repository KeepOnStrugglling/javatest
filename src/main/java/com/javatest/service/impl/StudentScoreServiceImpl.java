package com.javatest.service.impl;

import com.javatest.dao.StudentScoreMapper;
import com.javatest.po.StudentScore;
import com.javatest.service.StudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentScoreServiceImpl implements StudentScoreService {

    @Autowired
    private StudentScoreMapper mapper;

    @Override
    public StudentScore selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StudentScore> queryStudentScore(StudentScore studentScore) {
        return mapper.queryStudentScore(studentScore);
    }
}

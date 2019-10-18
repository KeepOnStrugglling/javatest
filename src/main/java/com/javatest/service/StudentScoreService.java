package com.javatest.service;

import com.javatest.po.StudentScore;

import java.util.List;

public interface StudentScoreService {

    StudentScore selectByPrimaryKey(Long id);

    List<StudentScore> queryStudentScore(StudentScore studentScore);
}

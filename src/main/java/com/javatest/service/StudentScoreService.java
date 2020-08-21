package com.javatest.service;

import com.javatest.domain.StudentScore;

import java.util.List;

public interface StudentScoreService {

    StudentScore selectByPrimaryKey(Long id);

    List<StudentScore> queryStudentScore(StudentScore studentScore);

    int variableTest(List<String> list);

    void variableTest2(int c);

    void doUpdate();

    String update(StudentScore studentScore);
}

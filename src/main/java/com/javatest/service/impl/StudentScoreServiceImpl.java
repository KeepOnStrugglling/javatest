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

    /**
     * 这个是用来测试方法调用传参是地址值
     * @param list
     * @return
     */
    @Override
    public int variableTest(List<String> list) {
        list.add("11");
        return 0;
    }

    /**
     * 这个是用来测试方法调用传参是地址值
     * @param c
     */
    @Override
    public void variableTest2(int c) {
        c = 2;
        System.out.println("method2 c=" + c);
    }

    @Override
    public void doUpdate() {
        StudentScore s = new StudentScore();
        s.setId(10015L);
        s.setScore(99);
        mapper.updateByPrimaryKeySelective(s);
        System.out.println(mapper.selectByPrimaryKey(10015L));
    }

    @Override
    public String update(StudentScore studentScore) {
        if ("JoJo".equals(studentScore.getName())) {
            studentScore.setScore(99);
        }
        mapper.updateByPrimaryKeySelective(studentScore);
        return studentScore.toString();
    }
}

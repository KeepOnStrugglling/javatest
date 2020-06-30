package com.javatest.service.impl;

import com.javatest.dao.StudentScoreMapper;
import com.javatest.po.StudentScore;
import com.javatest.po.StudentScore4JRS;
import com.javatest.service.StudentScoreService4JRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用JRS提供restful风格的接口
 */
@Service
@Transactional
public class StudentScoreServiceImpl4JRS implements StudentScoreService4JRS {

    @Autowired
    private StudentScoreMapper mapper;

    @Override
    public StudentScore4JRS selectByPrimaryKey(Long id) {
        return trans(mapper.selectByPrimaryKey(id));
    }

    @Override
    public List<StudentScore4JRS> queryStudentScore(StudentScore4JRS studentScore) {
        return transList(mapper.queryStudentScore(deTrans(studentScore)));
    }

    /*
     * 以下三个接口用于StudentScore4JRS与StudentScore的转化，只是为了少写dao层和避免创建新的表而已，并不是JRS的核心内容
     */
    private List<StudentScore4JRS> transList(List<StudentScore> queryStudentScores) {
        List<StudentScore4JRS> studentScore4JRSs = new ArrayList<>();
        for (StudentScore queryStudentScore : queryStudentScores) {
            studentScore4JRSs.add(trans(queryStudentScore));
        }
        return studentScore4JRSs;
    }

    private StudentScore4JRS trans(StudentScore studentScore) {
        return new StudentScore4JRS(studentScore.getId(),studentScore.getName(),studentScore.getScore());
    }

    private StudentScore deTrans(StudentScore4JRS studentScore) {
        return new StudentScore(studentScore.getId(),studentScore.getName(),studentScore.getScore());
    }


}

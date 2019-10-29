package com.javatest;

import com.javatest.dao.StudentScoreMapper;
import com.javatest.po.StudentScore;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaTestApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleTest {

    @Autowired
    private StudentScoreMapper studentScoreMapper;

    @Test
    public void test1() {
        StudentScore studentScore = new StudentScore();
        studentScore.setName("狗蛋");
        studentScore.setScore(88);

        int cn = studentScoreMapper.insert(studentScore);
        System.out.println(cn);
    }
    @Test
    public void test2() {
        StudentScore studentScore = new StudentScore();
        studentScore.setName("狗蛋2");

        int cn = studentScoreMapper.insertSelective(studentScore);
        System.out.println(cn);
    }
    @Test
    public void test3() {
        StudentScore studentScore = new StudentScore();
        studentScore.setId(10015L);
        studentScore.setName("狗蛋10086");
        studentScore.setScore(99);

        int cn = studentScoreMapper.updateByPrimaryKeySelective(studentScore);
        System.out.println(cn);
    }
    @Test
    public void test4() {
        StudentScore studentScore = new StudentScore();
        studentScore.setId(10016L);
        studentScore.setName("狗蛋10010");
        studentScore.setScore(77);

        int cn = studentScoreMapper.updateByPrimaryKey(studentScore);
        System.out.println(cn);
    }
    @Test
    public void test5() {
        StudentScore studentScore = studentScoreMapper.selectByPrimaryKey(10015L);
        System.out.println(studentScore.getId()+"="+studentScore.getName()+"="+studentScore.getScore());
    }
    @Test
    public void test6() {
        StudentScore studentScore = new StudentScore();
        List<StudentScore> studentScores = studentScoreMapper.queryStudentScore(studentScore);
        for (int i = 0; i <studentScores.size() ; i++) {
            System.out.println(studentScores.get(i).getId()+"="+studentScores.get(i).getName()+"="+studentScores.get(i).getScore());
        }
    }

    @Test
    public void test7(){
        String[] logoutOrderArr = StringUtils.split(null,",");
        System.out.println(logoutOrderArr);
    }
}
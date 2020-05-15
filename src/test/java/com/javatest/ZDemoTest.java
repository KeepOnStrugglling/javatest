package com.javatest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.javatest.po.StudentScore;
import com.javatest.util.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
public class ZDemoTest {

    @Test
    public void test1(){
        StudentScore s = new StudentScore(33L,"ken",88);
        System.out.println(JacksonUtil.obj2json(s));
    }

    @Test
    public void test2(){
        String json = "{\"id\":33,\"name\":\"ken\",\"score\":88}";
        System.out.println(JacksonUtil.json2pojo(json,StudentScore.class));
    }

    @Test
    public void test3(){
        String json = "{\"id\":33,\"name\":\"ken\",\"score\":88}";
        System.out.println(JacksonUtil.json2pojo(json, new TypeReference<StudentScore>() {
        }));
    }

    @Test
    public void test4(){
        String json = "[{\"id\":33,\"name\":\"ken\",\"score\":88},{\"id\":34,\"name\":\"den\",\"score\":66},{\"id\":35,\"name\":\"een\",\"score\":10}]";
        List<StudentScore> studentScores = JacksonUtil.json2list(json, StudentScore.class);
        System.out.println(studentScores);
    }

    @Test
    public void test5(){
        String json = "[{\"id\":33,\"name\":\"ken\",\"score\":88},{\"id\":34,\"name\":\"den\",\"score\":66},{\"id\":35,\"name\":\"een\",\"score\":10}]";
        List<Object> studentScores = JacksonUtil.json2listDeep(json);
        System.out.println(studentScores);
    }

    // 待测试
    @Test
    public void test6(){
        String json = "[[{\"id\":33,\"name\":\"ken\",\"score\":88},{\"id\":34,\"name\":\"den\",\"score\":66}],[{\"id\":35,\"name\":\"een\",\"score\":10}]]";
        List<Object> studentScores = JacksonUtil.json2listDeep(json);
        System.out.println(studentScores);
    }

    @Test
    public void test7(){
        String json = "[{\"id\":33,\"name\":\"ken\",\"score\":88},{\"id\":34,\"name\":\"den\",\"score\":66},{\"id\":35,\"name\":\"een\",\"score\":10}]";
        Set<StudentScore> studentScores = JacksonUtil.json2set(json,StudentScore.class);
        System.out.println(studentScores);
    }

    @Test
    public void test8(){
        String json = "{\"id\":33,\"name\":\"ken\",\"score\":88}";
        Map<String, Object> map = JacksonUtil.json2map(json);
        System.out.println(map);
    }

    @Test
    public void test9(){
        String json = "{\"student\":{\"id\":33,\"name\":\"ken\",\"score\":88}}";
        Map<String, StudentScore> map = JacksonUtil.json2map(json,StudentScore.class);
        System.out.println(map);
    }

    @Test
    public void test10(){
        String json = "[{\"student\":{\"id\":33,\"name\":\"ken\",\"score\":88}},{\"student\":{\"id\":33,\"name\":\"ken\",\"score\":88}}]";
        List<Object> list = JacksonUtil.json2listDeep(json);
        System.out.println(list);
    }

    @Test
    public void test11(){
        String json = "{\"student\":{\"id\":33,\"name\":\"ken\",\"score\":88},\"student2\":{\"id\":34,\"name\":\"den\",\"score\":66}}";
        Map<String, Object> map = JacksonUtil.json2mapDeep(json);
        System.out.println(map);
    }

    @Test
    public void test12(){
        String json = "{\"student\":[{\"id\":33,\"name\":\"ken\",\"score\":88},{\"id\":35,\"name\":\"een\",\"score\":77}],\"student2\":{\"id\":34,\"name\":\"den\",\"score\":66}}";
        Map<String, Object> map = JacksonUtil.json2mapDeep(json);
        System.out.println(map);
    }
}

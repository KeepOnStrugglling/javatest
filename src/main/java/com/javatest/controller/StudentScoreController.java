package com.javatest.controller;

import com.javatest.po.StudentScore;
import com.javatest.service.StudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentScoreController {

    @Autowired
    private StudentScoreService service;

    @GetMapping("/findOneShort/{id}")
    public StudentScore findStudentScoreByIdShort(@PathVariable("id") Long id) {
        return service.selectByPrimaryKey(id);
    }

    @GetMapping("/findOneLong/{id}")
    public StudentScore findStudentScoreByIdLong(@PathVariable("id") Long id) {
        try {
            Thread.sleep(5000);    // 后端模拟获取新数据的耗时，实际上可以做一个死循环判断数据是否有更新
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return service.selectByPrimaryKey(id);
    }

}

package com.javatest.controller;

import com.javatest.util.annotation.OperLog;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/aop")
public class AopController {

    @GetMapping("/head/{id}/{Pass}")
    @OperLog(type = "测试",module = "Aop",requestDes = "测试正常情况下的日志记录")
    public String head(@PathVariable("id") String id, @PathVariable("Pass") String password){
        System.out.println(id);
        System.out.println(password);
        return "head";
    }

    @PostMapping("/head2")
    @OperLog(type = "测试",module = "Aop",requestDes = "测试正常情况下的日志记录")
    public String head2(String id, String password){
//        System.out.println(id);
//        System.out.println(password);
        return "head";
    }

    @RequestMapping("/error")
    public String error(){
        int i = 1/0;
        return "error";
    }
}

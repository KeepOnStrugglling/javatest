package com.javatest.controller;

import com.javatest.exception.MyException;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excep")
public class ExceptionController {

    @RequestMapping("/error")
    public String error(){
        int i = 1/0;
        return "error";
    }

    @RequestMapping("/error2")
    public String error2(){
        throw new NullPointerException("空指针异常");
    }

    @SneakyThrows   // lombok的注解，强制抛出异常，仅建议在测试/学习时使用
    @RequestMapping("/error3")
    public String error3(){
        throw new MyException("自定义异常");
    }
}

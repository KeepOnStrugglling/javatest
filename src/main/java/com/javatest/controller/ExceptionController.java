package com.javatest.controller;

import com.javatest.exception.MyException;
import com.javatest.response.Result;
import com.javatest.util.HttpRequestUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/excep")
public class ExceptionController {

    @Autowired
    private HttpRequestUtil httpRequestUtil;

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

    @RequestMapping("/normal")
    public String normal(){
        return "ok";
    }

    @RequestMapping("/normal1")
    public Result normal1(){
        return Result.success("ok");
    }

    @RequestMapping("/normal2")
    public String normal2(HttpServletRequest request, HttpServletResponse response){
        return httpRequestUtil.dispatch("http://localhost:9010/javatest/excep/cookie",request,response);
    }

    @RequestMapping("/cookie")
    public Result cookie(HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            sb.append(cookie.getName()).append(":").append(cookie.getValue()).append(";");
        }
        return Result.success(sb.toString());
    }
}

package com.javatest.controller;

import com.javatest.annotation.ResponseResult;
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
@ResponseResult
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

    @RequestMapping("/dispatch")
    public String dispatch(HttpServletRequest request, HttpServletResponse response){
        Map<String,String> cookies = new HashMap<>();
        cookies.put("testCookie","15973");
        String dispatch = httpRequestUtil.dispatch("http://127.0.0.1:9010/javatest/excep/cookie", cookies, request, response);
        return dispatch;
    }

    @RequestMapping("/cookie")
    public Result cookie(HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            sb.append("没有找到cookie");
        } else {
            for (Cookie cookie : cookies) {
                sb.append(cookie.getName()).append(":").append(cookie.getValue()).append("-").append(cookie.getDomain()).append(";");
            }
        }
        return Result.success(sb.toString());
    }

    @RequestMapping("/addCookie")
    public String addCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("test","123456");
        cookie.setDomain("localhost");
        cookie.setPath("/javatest");
        response.addCookie(cookie);
        return "ok";
    }
}

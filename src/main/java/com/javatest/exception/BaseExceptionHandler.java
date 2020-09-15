package com.javatest.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    private final static int ERROR_CODE = 9999;

    // 用于处理指定的异常
    @ExceptionHandler(NullPointerException.class)
    public BaseException formNullPointerException(NullPointerException e) {
        return new MyException(e.getMessage());
    }

    // 用于处理自定义异常
    @ExceptionHandler(MyException.class)
    public BaseException formMyException(MyException e) {
        return new MyException(e.getMessage());
    }

    // 用于处理其他异常
    @ExceptionHandler(Exception.class)
    public BaseException formBaseException(Exception e) {
        return new BaseException(ERROR_CODE,e.getMessage());
    }

}

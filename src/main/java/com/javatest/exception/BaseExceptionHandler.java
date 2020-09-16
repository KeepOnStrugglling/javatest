package com.javatest.exception;

import com.javatest.enums.ReturnCode;
import com.javatest.response.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    private final static int ERROR_CODE = 9999;

    // 用于处理指定的异常
    @ExceptionHandler(NullPointerException.class)
    public <T> Result<T> formNullPointerException(T data) {
        return Result.fail(new BaseException(ReturnCode.NULL_POINTER),data);
    }

    // 用于处理自定义异常
    @ExceptionHandler(MyException.class)
    public Result formMyException() {
        return Result.fail(new BaseException(ReturnCode.MY_EXCEPTION));
    }

    // 用于处理其他异常
    @ExceptionHandler(Exception.class)
    public BaseException formBaseException(Exception e) {
        return new BaseException(ERROR_CODE,e.getMessage());
    }

}

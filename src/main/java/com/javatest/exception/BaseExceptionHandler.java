package com.javatest.exception;

import com.javatest.enums.ReturnCode;
import com.javatest.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    private final static int ERROR_CODE = 9999;

    /**
     * 用于处理指定的异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result formNullPointerException(NullPointerException e) {
        log.error("==========执行报错===========\n", e);
        return Result.fail(ReturnCode.NULL_POINTER);
    }

    /**
     * 用于处理自定义异常
     */
    @ExceptionHandler(MyException.class)
    public Result formMyException(MyException e) {
        log.error("==========执行报错===========\n", e);
        return Result.fail(ReturnCode.MY_EXCEPTION);
    }

    /**
     * 用于处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result formBaseException(Exception e) {
        log.error("==========执行报错===========\n", e);
        return Result.fail(new BaseException(ERROR_CODE,e.getMessage()));
    }

}

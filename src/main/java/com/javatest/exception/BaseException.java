package com.javatest.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author azure
 * 自定义基础异常
 */
@Getter
@Setter
@NoArgsConstructor
public class BaseException extends Exception {

    // 异常状态码
    private int code;
    // 异常信息
    private String msg;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}

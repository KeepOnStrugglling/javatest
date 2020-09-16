package com.javatest.exception;

import com.javatest.enums.ReturnCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author azure
 * 自定义基础异常
 */
@Data
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

    // 根据returnCode构造异常
    public BaseException(ReturnCode returnCode) {
        super(returnCode.getMsg());
        this.code = returnCode.getCode();
    }
}

package com.javatest.exception;

import com.javatest.enums.ReturnCode;

public class BusinessException extends BaseException {

    public BusinessException(String msg) {
        super(ReturnCode.Business_EXCEPTION);
    }
}

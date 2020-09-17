package com.javatest.exception;

import com.javatest.enums.ReturnCode;

public class MyException extends BaseException {

    public MyException(String msg) {
        super(ReturnCode.MY_EXCEPTION);
    }
}

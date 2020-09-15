package com.javatest.exception;

public class MyException extends BaseException {

    private final static int ERROR_CODE = 5001;

    public MyException(String msg) {
        super(ERROR_CODE,msg);
    }
}

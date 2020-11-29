package com.javatest.util;

import com.javatest.exception.BaseException;

public interface Assert {

    BaseException newException(Object... args);

    BaseException newException(Throwable t, Object... args);

    default void assertNotNull(Object o) throws BaseException {
        if (o == null) {
            throw newException(o);
        }
    }

    default void assertNotNull(Object o, Object... args) throws BaseException {
        if (o == null) {
            throw newException(args);
        }
    }
}

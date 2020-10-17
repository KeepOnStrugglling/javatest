package com.javatest.annotation;

import java.lang.annotation.*;

/**
 * 用于自动封装响应对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface ResponseResult {
}

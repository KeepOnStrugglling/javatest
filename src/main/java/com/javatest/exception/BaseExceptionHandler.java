package com.javatest.exception;

import com.javatest.enums.ReturnCode;
import com.javatest.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler implements ResponseBodyAdvice<Object> {

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
    public Result formBusinessException(MyException e) {
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


    /**  用于封装正常的返回值，需要配合@ResponseResult自定义注解  **/
    // 标识
    private static final String RESPONSE_RESULT = "RESPONSE_RESULT";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 判断请求是否有标识
        return request.getAttribute(RESPONSE_RESULT) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 如果返回值是Result类型或String类型则直接返回
        /* 之所以String类型直接返回：
            1)String类型的返回值会触发转换出错的异常（估计是转换器的缘故）
            2)返回值为Json字符串时，不必要再次封装
        */
        if (body instanceof Result || body instanceof String) {
            return body;
        }
        return Result.success(body);
    }

}

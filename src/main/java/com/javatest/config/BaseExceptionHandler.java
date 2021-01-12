package com.javatest.config;

import com.javatest.enums.ReturnCode;
import com.javatest.exception.BaseException;
import com.javatest.exception.MyException;
import com.javatest.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
     * 处理jsr303规范校验的错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidException(MethodArgumentNotValidException e) {
        // 获取异常中的校验错误信息
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item -> {
            // 获取校验失败字段
            String field = item.getField();
            // 获取校验失败的提示信息
            String message = item.getDefaultMessage();
            map.put(field,message);
        });
        return Result.fail(ReturnCode.PARAM_VALID_EXCEPTION, map);
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

    /**
     * 重写ResponseBodyAdvice接口提供的beforeBodyWrite方法修改返回值
     * @param body 请求的响应内容，即controller方法返回的值，可以在本方法进行调整
     * @param returnType controller方法的详细信息，里面包含返回值、方法的信息如入参类型、返回类型等
     * @param selectedContentType 返回的contentType，如application/json
     * @param selectedConverterType 返回时使用到的转换器
     * @param request 请求
     * @param response 响应
     * @return 处理后实际返回给前端的内容
     */
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

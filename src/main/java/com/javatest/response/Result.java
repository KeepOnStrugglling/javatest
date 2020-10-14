package com.javatest.response;

import com.javatest.enums.ReturnCode;
import com.javatest.exception.BaseException;
import lombok.*;

import java.io.Serializable;

/**
 * @author azure
 * 统一返回值
 * @param <T> 返回值类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    // 返回码
    private Integer code;
    // 返回值
    private T data;
    // 错误信息
    private String msg;

    /**
     * 添加一个构造方法，用于使用ReturnCode
     */
    public Result(ReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
    }

    /**
     * 添加一个构造方法，用于使用ReturnCode
     */
    public Result(ReturnCode returnCode, T data) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.data = data;
    }

    // 成功调用的方法
    public static Result success() {
        return new Result(ReturnCode.SUCCESS);
    }

    // 成功调用的方法，含数据
    public static <T> Result<T> success(T data) {
        return new Result(ReturnCode.SUCCESS,data);
    }

    // 失败调用的方法
    public static Result fail(ReturnCode returnCode) {
        return new Result(returnCode);
    }

    // 失败调用的方法，含数据
    public static <T> Result<T> fail(ReturnCode returnCode, T data) {
        return new Result(returnCode,data);
    }

    // 异常调用的方法
    public static Result<BaseException> fail(BaseException e) {
        return new Result<>(e.getCode(),null,e.getMsg());
    }

    // 异常调用的方法，含数据
    public static <T> Result<T> fail(BaseException e, T data) {
        return new Result<>(e.getCode(),data,e.getMsg());
    }

    // 异常调用的方法，含数据
    public static <T> Result fail(BaseException e, T data) {
        return new Result(e.getCode(),data,e.getMsg());
    }
}

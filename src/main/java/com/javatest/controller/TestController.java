package com.javatest.controller;

import com.javatest.domain.BrandEntity;
import com.javatest.enums.ReturnCode;
import com.javatest.exception.valid.AddGroup;
import com.javatest.exception.valid.UpdateGroup;
import com.javatest.response.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController("test")
public class TestController {

//    /**
//     * jsr303校验方式
//     */
//    @RequestMapping("/save")
//    public Result save(@RequestBody @Valid BrandEntity brand, BindingResult bindingResult){
//        // 如果验证出问题，可以从bindingResult中获取数据
//        if (bindingResult.hasErrors()) {
//            Map<String, String> map = new HashMap<>();
//            bindingResult.getFieldErrors().forEach(item -> {
//                // 获取校验失败字段
//                String field = item.getField();
//                // 获取校验失败的提示信息
//                String message = item.getDefaultMessage();
//                map.put(field,message);
//            });
//            return Result.fail(ReturnCode.PARAM_VALID_EXCEPTION, map);
//        }
//
//        // 具体业务逻辑，此略
//
//        return Result.success();
//    }

//    /**
//     * 使用统一异常处理的校验
//     */
//    @RequestMapping("/save")
//    public Result save(@RequestBody @Valid BrandEntity brand){
//        // 具体业务逻辑，此略
//
//        return Result.success();
//    }

    /**
     * 使用校验分组，指定分组为Add
     */
    @RequestMapping("/save")
    public Result save(@RequestBody @Validated({AddGroup.class}) BrandEntity brand){
        // 具体业务逻辑，此略

        return Result.success();
    }

    /**
     * 使用校验分组，指定分组为Update
     */
    @RequestMapping("/update")
    public Result update(@RequestBody @Validated({UpdateGroup.class}) BrandEntity brand){
        // 具体业务逻辑，此略

        return Result.success();
    }
}

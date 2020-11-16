package com.javatest.controller;

import com.javatest.annotation.ResponseResult;
import com.javatest.domain.StudentScore;
import com.javatest.service.StudentScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentScoreController {

    @Autowired
    private StudentScoreService service;

    @ResponseResult
    @GetMapping("/findOneShort/{id}")
    public StudentScore findStudentScoreByIdShort(@PathVariable("id") Long id) {
        return service.selectByPrimaryKey(id);
    }

    @GetMapping("/findOneLong/{id}")
    public StudentScore findStudentScoreByIdLong(@PathVariable("id") Long id) {
        try {
            Thread.sleep(5000);    // 后端模拟获取新数据的耗时，实际上可以做一个死循环判断数据是否有更新
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return service.selectByPrimaryKey(id);
    }

    /**
     * 研究使用javax.validation的注解验证入参
     */
    @PostMapping("/update")
    // 用@Valid表示要启用验证，如果不加则不会验证入参
    // BindingResult用于接收验证的结果
    public String update(@Valid StudentScore studentScore, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 将自定义的错误信息返回给前端
            return bindingResult.getFieldError().getDefaultMessage();
        }
        return service.update(studentScore);
    }

    /**
     * 研究返回字符串为中文时乱码的情况
     *   原因：Spring通过HttpMessageConverter接口进行格式转换，而处理String类型的StringHttpMessageConverter指定默认码表为ISO-8859-1，所以乱码
     *   解决方法1：在RequestMapping中指定produces，那么返回值将按照produces的格式和码表转换
     *   解决方法2：将返回值类型改为集合类型或者对象，此时不会调用StringHttpMessageConverter转换，自然就避免了乱码问题
     *   解决方法3：在SpringBoot2.0和Spring5.0情况下，实现WebMvcConfigurer或继承WebMvcConfigurationSupport。否则可以继承WebMvcConfigurerAdapter
     */
    @PostMapping(value = "/update2")
//    @PostMapping(value = "/update2",produces = "text/plain;charset=UTF-8")
    public String update2(@Valid StudentScore studentScore, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 将自定义的错误信息返回给前端
            return bindingResult.getFieldError().getDefaultMessage();
        }
        return service.update(studentScore);
    }

    /**
     * 使用统一异常处理类进行
     * @param studentScore
     * @return
     */
    @PostMapping(value = "/update3")
    public String update3(@Valid StudentScore studentScore) {
        return service.update(studentScore);
    }
}

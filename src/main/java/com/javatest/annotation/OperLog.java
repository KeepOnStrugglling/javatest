package com.javatest.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE}) //注解可使用在方法、类/接口级别上
@Retention(RetentionPolicy.RUNTIME) //注解执行的阶段为运行时，以便在运行时获取注解信息
@Inherited  // 该注解可以被子类继承，非必须
@Documented // 执行javadoc会自动生成文档，非必须
public @interface OperLog {
    String type() default "";       // 操作类型
    String module() default "";     // 操作模块
    String requestDes() default ""; // 操作描述
}

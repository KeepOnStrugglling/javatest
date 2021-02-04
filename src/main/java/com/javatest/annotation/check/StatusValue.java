package com.javatest.annotation.check;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { StatusValueConstraintValidator.class })
public @interface StatusValue {

    // 加上{}就会在配置文件中获取默认错误信息
    String message() default "{com.javatest.annotation.check.StatusValue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    // 自定义的属性
    int[] vals() default { };
}


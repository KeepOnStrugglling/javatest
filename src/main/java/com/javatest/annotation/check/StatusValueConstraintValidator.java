package com.javatest.annotation.check;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义校验器
 * 需要实现ConstraintValidator接口，其中传入的两个泛型，第一个为自定义注解，第二个为该自定义注解作用的字段的类型
 * 比如自定义注解StatusValue是注解在Integer类型的字段showStatus，所以实现如下
 */
public class StatusValueConstraintValidator implements ConstraintValidator<StatusValue,Integer> {

    // 存储自定义注解中指定的范围值
    private Set<Integer> set = new HashSet<>();

    /**
     * 初始化方法
     * @param constraintAnnotation 自定义注解
     */
    @Override
    public void initialize(StatusValue constraintAnnotation) {

        // 获取注解的自定义属性的值
        int[] vals = constraintAnnotation.vals();
        // 将注解指定的范围值放入set中
        for (int val : vals) {
            set.add(val);
        }
    }

    /**
     * 判断是否校验成功
     * @param value 需要校验的值，即前端传来的自定义注解所标注的属性的值
     * @param context 校验的上下文信息
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 将传入值与指定范围值进行比对
        return set.contains(value);
    }
}

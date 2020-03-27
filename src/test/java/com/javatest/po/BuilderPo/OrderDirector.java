package com.javatest.po.BuilderPo;

public class OrderDirector {
    private AbstractMealBuilder builder;

    public OrderDirector(AbstractMealBuilder builder) {
        this.builder = builder;
    }

    public Meal construct() {
        // 构造复杂的流程
        builder.prepareVegMeal();
        builder.prepareNonVegMeal();
        builder.prepareVegMeal();
        return builder.getMeal();   // 返回复杂对象
    }
}

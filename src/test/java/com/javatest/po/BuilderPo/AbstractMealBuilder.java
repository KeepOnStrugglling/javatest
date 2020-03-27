package com.javatest.po.BuilderPo;

public abstract class AbstractMealBuilder {

    protected Meal meal = new Meal();
    public abstract void prepareVegMeal();
    public abstract void prepareNonVegMeal();
    public abstract Meal getMeal();
}

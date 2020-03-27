package com.javatest.po.BuilderPo;

public class Order2 extends AbstractMealBuilder {
    @Override
    public void prepareVegMeal() {
        meal.addItem(new VegBurger());
        meal.addItem(new Pepsi());
    }

    @Override
    public void prepareNonVegMeal() {
        meal.addItem(new MeatBurger());
        meal.addItem(new Pepsi());
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}

package com.javatest.po.BuilderPo;

public class MeatBurger extends Burger{
    @Override
    public String name() {
        return "牛肉汉堡";
    }

    @Override
    public float price() {
        return 40.0f;
    }
}

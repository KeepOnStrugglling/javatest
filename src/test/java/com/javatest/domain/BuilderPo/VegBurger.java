package com.javatest.domain.BuilderPo;

public class VegBurger extends Burger {
    @Override
    public String name() {
        return "素菜汉堡";
    }

    @Override
    public float price() {
        return 25.0f;
    }
}

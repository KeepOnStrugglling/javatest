package com.javatest.domain.BuilderPo;

public class Coke extends Drink {
    @Override
    public String name() {
        return "可口可乐";
    }

    @Override
    public float price() {
        return 10.0f;
    }
}

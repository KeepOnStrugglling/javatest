package com.javatest.domain.BuilderPo;

public class Pepsi extends Drink {
    @Override
    public String name() {
        return "百事可乐";
    }

    @Override
    public float price() {
        return 10.5f;
    }
}

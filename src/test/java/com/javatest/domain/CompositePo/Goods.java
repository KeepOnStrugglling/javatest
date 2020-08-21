package com.javatest.domain.CompositePo;

public class Goods implements Subject {
    private String name;
    private float price;
    private int quantity;

    public Goods(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public float sum() {
        return price*quantity;
    }

    @Override
    public void show() {
        System.out.print(name+"(数量："+quantity+"，单价："+price+"元),");
    }
}

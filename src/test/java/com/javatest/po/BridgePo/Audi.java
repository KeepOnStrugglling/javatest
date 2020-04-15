package com.javatest.po.BridgePo;

public class Audi extends Car{

    public Audi(Color color) {
        super(color);
    }

    @Override
    public void produce() {
        color.printColor();
        System.out.println("Audi生产完毕！");
    }
}

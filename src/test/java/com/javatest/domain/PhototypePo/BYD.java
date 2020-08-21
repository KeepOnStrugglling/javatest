package com.javatest.domain.PhototypePo;

public class BYD implements Car {

    public BYD() {
        System.out.println("具体实例被new创建");
    }

    @Override
    public void drive() {
        System.out.println("BYD风驰电掣！");
    }

    @Override
    public Car clone() {
        Car byd = null;
        try {
            byd = (BYD) super.clone();
            System.out.println("具体实例被复制");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return byd;
    }
}

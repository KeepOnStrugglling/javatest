package com.javatest.domain.BridgePo;

public class BYD extends Car {

    public BYD(Color color) {
        super(color);
    }

    @Override
    public void produce() {
        //将实例化角色的功能整合进来，使具体抽象化角色也拥有实例化角色的功能，变相实现了抽象和实现的结合
        color.printColor();
        System.out.println("BYD生产完毕！");
    }
}

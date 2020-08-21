package com.javatest.domain.BridgePo;

public abstract class Car {
    // 将实现化角色整合进来,注意用protected，让子类能直接使用
    protected Color color;
    // 带参构造可以是protected，也可以是public
    public Car(Color color) {
        this.color = color;
    }
    public abstract void produce();
}

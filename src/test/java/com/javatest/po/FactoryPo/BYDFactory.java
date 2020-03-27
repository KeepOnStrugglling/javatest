package com.javatest.po.FactoryPo;

public class BYDFactory extends AbstractFactory{
    public Car getCar() {
        System.out.println("造汽车啦。。。");
        return new BYD();
    }
    public Mask getMask() {
        System.out.println("造口罩啦。。。");
        return new MedicalMask();
    }
}
package com.javatest.domain.FactoryPo;

public class VWFactory extends AbstractFactory{
    public Car getCar() {
        System.out.println("造汽车啦。。。");
        return new VW();
    }
    public Mask getMask() {
        System.out.println("造口罩啦。。。");
        return new N95Mask();
    }
}

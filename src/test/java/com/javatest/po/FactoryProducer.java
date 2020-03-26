package com.javatest.po;

import com.javatest.FactoryTest;

// 工厂创建类
public class FactoryProducer{
    public static AbstractFactory getFactory(String type) {
        if(type.equalsIgnoreCase("BYD")) {
            return new BYDFactory();
        } else if(type.equalsIgnoreCase("VW")) {
            return new VWFactory();
        }
        return null;
    }
}
package com.javatest.domain.FactoryPo;

public class VW implements Car{
    @Override
    public void drive(){
        System.out.println("开大众");
    }
}
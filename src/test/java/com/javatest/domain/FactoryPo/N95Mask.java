package com.javatest.domain.FactoryPo;

public class N95Mask implements Mask{
    @Override
    public void produce(){
        System.out.println("生产N95口罩。。。");
    }
}
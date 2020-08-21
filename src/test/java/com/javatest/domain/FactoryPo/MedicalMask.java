package com.javatest.domain.FactoryPo;

public class MedicalMask implements Mask{
    @Override
    public void produce(){
        System.out.println("生产医用口罩。。。");
    }
}
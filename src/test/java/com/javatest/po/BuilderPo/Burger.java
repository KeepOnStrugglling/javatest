package com.javatest.po.BuilderPo;

public abstract class Burger implements Item {

    // 所有汉堡都是纸包装的，子类不需要设置，在此指定为纸包装即可
    @Override
    public Packing pack(){
        return new Wrapper();
    }

}

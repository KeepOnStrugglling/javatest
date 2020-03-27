package com.javatest.po.BuilderPo;

public abstract class Drink implements Item {

    // 所有饮料都是瓶包装的，子类不需要设置，在此指定为瓶包装即可
    @Override
    public Packing pack(){
        return new Bottle();
    }
}

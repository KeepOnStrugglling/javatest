package com.javatest.po.PhototypePo;

public interface Car extends Cloneable{

    // 对象共有的方法
    public void drive();

    // 原型模式中复制对象的方法
    public Object clone();
}

package com.javatest.po.PhototypePo;

public class N95 extends Mask {

    // 此时可以指定不同的属性值
    public N95(){
        color = "white";
        type = "N95";
        System.out.println("创建N95实例");
    }

    @Override
    public void produce() {
        System.out.println("生成N95口罩");
    }

}

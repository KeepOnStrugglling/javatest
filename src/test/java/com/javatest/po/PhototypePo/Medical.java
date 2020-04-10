package com.javatest.po.PhototypePo;

public class Medical extends Mask {
    // 此时可以指定不同的属性值
    public Medical(){
        color = "blue";
        type = "Medical";
        System.out.println("创建Medical实例");
    }

    @Override
    public void produce() {
        System.out.println("生成Medical口罩");
    }
}

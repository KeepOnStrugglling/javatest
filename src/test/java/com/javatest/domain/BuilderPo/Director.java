package com.javatest.domain.BuilderPo;

public class Director {

    private AbstractBuilder builder;

    // 为了提高灵活性，增加无参构造
    public Director() {
    }
    public void setBuilder(AbstractBuilder builder) {
        this.builder = builder;
    }

    // 根据传入的builder获取对应的Director，则此时已经指定了复杂对象每个部分的构建方法
    public Director(AbstractBuilder builder) {
        this.builder = builder;
    }

    public Product produce(){
        // 指定复杂对象的构造流程
        builder.buildPart2();
        builder.buildPart1();
        builder.buildPart1();
        builder.buildPart1();
        builder.buildPart3();
        builder.buildPart3();
        // 返回最终的复杂对象
        return builder.getProduct();
    }
}

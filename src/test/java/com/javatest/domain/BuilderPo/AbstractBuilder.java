package com.javatest.domain.BuilderPo;

public abstract class AbstractBuilder {

    // 需要生成的复杂对象。如果不考虑复用性，也可以将该行代码放入每个具体Builder中
    // 注意：为了让子类能够访问product，访问权限不能为private，建议为protected
    protected Product product = new Product();

    // 假设每个产品包含3个相互独立的方法用于构建产品的不同部分
    public abstract void buildPart1();
    public abstract void buildPart2();
    public abstract void buildPart3();

    // 通常来说是要将生成的对象返回的，为避免子类遗漏该方法，建议在父类中添加返回的方法
    public abstract Product getProduct();
}

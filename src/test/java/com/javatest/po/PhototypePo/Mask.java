package com.javatest.po.PhototypePo;

public abstract class Mask implements Cloneable {

    // 指定成员变量
    protected String color;

    public void setColor(String color) {
        this.color = color;
    }

    protected String type;

    // 指定成员方法
    public abstract void produce();

    // 在抽象类中必须重写clone方法，如果此处为抽象方法，则具体原型类无法重写clone方法
    @Override
    public Mask clone() throws CloneNotSupportedException {
        System.out.println("实例被复制");
        return (Mask)super.clone();
    }

    @Override
    public String toString() {
        return "Mask{" +
                "color='" + color + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

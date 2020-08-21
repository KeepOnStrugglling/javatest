package com.javatest.domain.DecoratorPo;

public class Saber implements Hero {
    private String name;

    public Saber(String name) {
        this.name = name;
    }

    @Override
    public void skillAttack() {
        System.out.println(name + "释放技能！");
    }
}

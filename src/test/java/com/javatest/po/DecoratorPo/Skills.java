package com.javatest.po.DecoratorPo;

public class Skills implements Hero {
    private Hero hero;

    public Skills(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void skillAttack() {
        hero.skillAttack();
    }
}

package com.javatest;

import com.javatest.domain.DecoratorPo.*;

public class DecoratorTest {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test2() {
        Hero saber = new Saber("呆毛");
        saber.skillAttack();
        System.out.println("__________修行中_________");
        Skills daimao;
        daimao = new Skill_E(saber);
        daimao = new Skill_Q(daimao);
        daimao = new Skill_W(daimao);
        daimao.skillAttack();
    }

    private static void test1() {
        Component component = new ConcreteComponent();
        component.operation();
        System.out.println("======================");
        Decorator decorator = new ConcreteDecorator(component);
        decorator.operation();
    }
}

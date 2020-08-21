package com.javatest.domain.DecoratorPo;

public class Skill_E extends Skills {
    public Skill_E(Hero hero) {
        super(hero);
    }

    @Override
    public void skillAttack() {
        super.skillAttack();
        System.out.println("SkillA:风王结界！");
    }
}

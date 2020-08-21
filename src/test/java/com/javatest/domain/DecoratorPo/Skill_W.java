package com.javatest.domain.DecoratorPo;

public class Skill_W extends Skills {
    public Skill_W(Hero hero) {
        super(hero);
    }

    @Override
    public void skillAttack() {
        super.skillAttack();
        System.out.println("SkillW:真名释放-Excalibur！");
    }
}

package com.javatest.domain.DecoratorPo;

public class Skill_Q extends Skills {
    public Skill_Q(Hero hero) {
        super(hero);
    }

    @Override
    public void skillAttack() {
        super.skillAttack();
        System.out.println("SkillQ:魔力释放！");
    }
}

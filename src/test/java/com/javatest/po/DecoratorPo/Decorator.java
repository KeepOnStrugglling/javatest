package com.javatest.po.DecoratorPo;

// 继承抽象构件
public class Decorator implements Component {
    // 包含具体构件，使抽象装饰角色具备具体构件的具体功能
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}

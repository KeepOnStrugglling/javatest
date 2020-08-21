package com.javatest.domain.DecoratorPo;

public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }

    // 自行重写装饰父类的operation()，并添加额外的功能
    @Override
    public void operation() {
        super.operation();
        // 添加额外的功能
        extraOperation();
    }

    private void extraOperation() {
        System.out.println("为具体构件角色增加额外的功能extraOperation()");
    }
}

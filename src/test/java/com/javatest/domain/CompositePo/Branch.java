package com.javatest.domain.CompositePo;

import java.util.ArrayList;
import java.util.List;

public class Branch implements Component {

    private String branchName;
    private List<Component> components = new ArrayList<>();

    public Branch(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void del(Component component) {
        components.remove(component);
    }

    @Override
    public Component getChild(int i) {
        return components.get(i);
    }

    @Override
    public void operation() {
        System.out.println("进入枝节点" + branchName);
        for (Component component : components) {
            component.operation();
        }
    }
}

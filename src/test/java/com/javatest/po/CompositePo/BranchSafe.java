package com.javatest.po.CompositePo;

import java.util.ArrayList;
import java.util.List;

public class BranchSafe implements ComponentSafe {

    private String branchName;
    private List<ComponentSafe> components = new ArrayList<>();

    public BranchSafe(String branchName) {
        this.branchName = branchName;
    }

    public void add(ComponentSafe component) {
        components.add(component);
    }

    public void del(ComponentSafe component) {
        components.remove(component);
    }

    public ComponentSafe getChild(int i) {
        return components.get(i);
    }

    @Override
    public void operation() {
        System.out.println("进入枝节点" + branchName);
        for (ComponentSafe component : components) {
            component.operation();
        }
    }
}

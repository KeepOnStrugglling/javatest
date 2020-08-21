package com.javatest.domain.CompositePo;

public class LeafSafe implements ComponentSafe {
    private String leafName;

    public LeafSafe(String leafName) {
        this.leafName = leafName;
    }

    @Override
    public void operation() {
        System.out.println("进入叶节点," + leafName + "被访问");
    }
}

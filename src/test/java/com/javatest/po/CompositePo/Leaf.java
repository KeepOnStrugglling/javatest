package com.javatest.po.CompositePo;

public class Leaf implements Component {

    private String leafName;

    public Leaf(String leafName) {
        this.leafName = leafName;
    }

    /*
     * 注意：叶节点没有增删查这类管理功能，需要空实现
     */
    @Override
    public void add(Component component) {
    }
    @Override
    public void del(Component component) {
    }

    @Override
    public Component getChild(int i) {
        return null;
    }

    @Override
    public void operation() {
        System.out.println("进入叶节点," + leafName + "被访问");
    }
}

package com.javatest.domain.CompositePo;

public interface Component {
    void add(Component component);
    void del(Component component);
    Component getChild(int i);
    void operation();

}

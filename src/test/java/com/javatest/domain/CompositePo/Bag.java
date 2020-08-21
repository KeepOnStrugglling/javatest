package com.javatest.domain.CompositePo;

import org.python.core.AstList;

import java.util.List;

public class Bag implements Subject {

    private String name;

    public Bag(String name) {
        this.name = name;
    }

    List<Subject> subjectList = new AstList();

    public void add(Subject subject){
        subjectList.add(subject);
    }
    public void remove(Subject subject){
        subjectList.remove(subject);
    }
    public Subject getChild(int i){
        return subjectList.get(i);
    }

    @Override
    public float sum() {
        float sum = 0;
        for (Subject subject : subjectList) {
            sum+=subject.sum();
        }
        return sum;
    }

    @Override
    public void show() {
        System.out.print(name + ":{");
        for (Subject subject : subjectList) {
            subject.show();
        }
        System.out.print("}");
    }
}

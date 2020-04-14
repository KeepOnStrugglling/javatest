package com.javatest.po.PhototypePo;

import java.io.Serializable;

public class Subject implements Cloneable, Serializable {
    private String code;
    private String subjectName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // 如果Subject对象也有成员变量是引用类型的，也需要进行下一层复制
        return super.clone();
    }

    @Override
    public String toString() {
        return "Subject{" +
                "code='" + code + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}

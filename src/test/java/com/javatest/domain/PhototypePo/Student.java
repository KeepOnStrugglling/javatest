package com.javatest.domain.PhototypePo;

import java.io.*;

public class Student implements Cloneable, Serializable {
    private String id;
    private String name;
    private Subject subject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Student clone() throws CloneNotSupportedException {
        // 深拷贝
        // 先拷贝一份原对象
        Student student = (Student) super.clone();
        // 对引用类型的成员变量进行拷贝
        student.subject = (Subject) subject.clone();
        return student;
    }

    // 使用序列化深拷贝
    public Student deepClone() throws IOException, ClassNotFoundException {
        // 将对象写入流中
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this);
        // 将流写入对象中
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (Student) objectInputStream.readObject();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", subject=" + subject +
                '}';
    }
}

package com.javatest;

import com.javatest.domain.PhototypePo.*;

import java.io.IOException;

public class PrototypeTest {
//    public static void main(String[] args) {
//        Car byd = new BYD();
//        Car byd1 = (Car) byd.clone();
//        Car byd2 = (Car) byd.clone();
//        byd.drive();
//        byd1.drive();
//        byd2.drive();
//    }
//    public static void main(String[] args) throws CloneNotSupportedException {
//        Mask mask = new N95();
//        System.out.println(mask.toString());
//        mask.produce();
//        Mask clone1 = mask.clone();
//        clone1.produce();
//        System.out.println(clone1.toString());
//
//        Mask mask1 = new Medical();
//        System.out.println(mask1.toString());
//        mask.produce();
//        Mask clone2 = mask1.clone();
//        clone2.produce();
//        System.out.println(clone2.toString());
//    }
//    public static void main(String[] args) throws CloneNotSupportedException {
//        ProtoTypeManager ptm = new ProtoTypeManager();
//        Mask mask = ptm.getMaskClone("N95");
//        System.out.println(mask.toString());
//        mask.setColor("black");
//        ptm.addMaskProto("new N95",mask);
//        Mask mask1 = ptm.getMaskClone("new N95");
//        System.out.println(mask1.toString());
//    }
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Student student1 = new Student();
        Subject subject = new Subject();
        subject.setCode("1");
        subject.setSubjectName("Math");
        student1.setId("1");
        student1.setName("JoJo");
        student1.setSubject(subject);
        // 复制
        Student student2 = student1.clone();
        student2.setId("2");
        student2.setName("Dio");
        student2.getSubject().setCode("2");
        student2.getSubject().setSubjectName("English");

        Student student3 = student1.deepClone();
        System.out.println(student1);
        System.out.println(student2);
        System.out.println(student3);
    }
}

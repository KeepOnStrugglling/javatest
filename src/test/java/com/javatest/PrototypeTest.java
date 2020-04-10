package com.javatest;

import com.javatest.po.PhototypePo.*;

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
    public static void main(String[] args) throws CloneNotSupportedException {
        ProtoTypeManager ptm = new ProtoTypeManager();
        Mask mask = ptm.getMaskClone("N95");
        System.out.println(mask.toString());
        mask.setColor("black");
        ptm.addMaskProto("new N95",mask);
        Mask mask1 = ptm.getMaskClone("new N95");
        System.out.println(mask1.toString());
    }
}

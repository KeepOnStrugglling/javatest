package com.javatest;

import com.javatest.po.BridgePo.*;

public class BridgeTest {
    public static void main(String[] args) {
        Color blue = new Blue();
        Car byd = new BYD(blue);
        Color red = new Red();
        Car audi = new Audi(red);
        byd.produce();
        audi.produce();
    }
}

package com.javatest;

import com.javatest.domain.AdapterPo.AdapterChange2;
import com.javatest.domain.AdapterPo.AmericanFan;

public class AdapterTest {
//    public static void main(String[] args) {
//        System.out.println("类适配器测试...");
//        AdapterChange ac = new AdapterChange();
//        ac.charge();
//    }
    public static void main(String[] args) {
        System.out.println("对象适配器测试...");
        AmericanFan americanFan = new AmericanFan();
        AdapterChange2 ac = new AdapterChange2(americanFan);
        ac.charge();
    }
}

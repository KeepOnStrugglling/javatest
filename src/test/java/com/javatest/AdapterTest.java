package com.javatest;

import com.javatest.po.AdapterPo.AdapterChange;
import com.javatest.po.AdapterPo.AdapterChange2;
import com.javatest.po.AdapterPo.AmericanFan;

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

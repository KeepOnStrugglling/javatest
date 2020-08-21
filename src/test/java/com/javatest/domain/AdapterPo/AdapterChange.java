package com.javatest.domain.AdapterPo;

public class AdapterChange extends AmericanFan implements HighVoltage {
    @Override
    public void charge() {
        System.out.println("适配器正在工作...");
        work();
    }
}

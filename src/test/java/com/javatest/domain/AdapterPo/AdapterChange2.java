package com.javatest.domain.AdapterPo;

public class AdapterChange2 implements HighVoltage {
    private AmericanFan americanFan;

    public AdapterChange2(AmericanFan americanFan) {
        this.americanFan = americanFan;
    }

    @Override
    public void charge() {
        System.out.println("适配器正在工作...");
        americanFan.work();
    }
}

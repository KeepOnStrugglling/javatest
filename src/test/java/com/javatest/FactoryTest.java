package com.javatest;

import com.javatest.domain.FactoryPo.AbstractFactory;
import com.javatest.domain.FactoryPo.Car;
import com.javatest.domain.FactoryPo.FactoryProducer;
import com.javatest.domain.FactoryPo.Mask;

import java.util.Scanner;

public class FactoryTest {
    // 测试类
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        AbstractFactory factory = FactoryProducer.getFactory(type);
        Car car = factory.getCar();
        car.drive();
        Mask mask = factory.getMask();
        mask.produce();
    }
}

package com.javatest;

import com.javatest.domain.BuilderPo.*;

public class BuilderTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String type = scanner.nextLine();
//        AbstractBuilder builder = null;
//        if ("1".equals(type)) {
//            builder = new Builder1();
//        } else if ("2".equals(type)) {
//            builder = new Builder2();
//        }
//        Director director = new Director(builder);
//        Product product = director.produce();
//        product.use();
//    }

    public static void main(String[] args) {
        OrderDirector director1 = new OrderDirector(new Order1());
        OrderDirector director2 = new OrderDirector(new Order2());
        Meal meal1 = director1.construct();
        Meal meal2 = director2.construct();
        System.out.println("Meal1:");
        meal1.showMeal();
        System.out.println("Total cost:" + meal1.getCost());
        System.out.println("Meal2:");
        meal2.showMeal();
        System.out.println("Total cost:" + meal2.getCost());
    }
}

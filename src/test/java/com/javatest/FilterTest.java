package com.javatest;

import com.javatest.domain.FilterPo.*;

import java.util.ArrayList;
import java.util.List;

public class FilterTest {
    public static void main(String[] args) {
        List<Product> list = new ArrayList<>();
        list.add(new Product("circle","blue"));
        list.add(new Product("square","blue"));
        list.add(new Product("circle","red"));
        list.add(new Product("square","red"));

        Criteria c = new CircleCriteria();
        Criteria s = new SquareCriteria();
        Criteria b = new BlueCriteria();

        //筛选蓝圈
        Criteria andCriteria = new AndCriteria(c,b);
        List<Product> r1 = andCriteria.filterProduct(list);
        r1.forEach(System.out::println);
        //筛选红方
        Criteria orCriteria = new OrCriteria(s,b);
        List<Product> r2 = orCriteria.filterProduct(list);
        r2.forEach(System.out::println);
        //筛选蓝方
        Criteria andCriteria1 = new AndCriteria(s,b);
        List<Product> r3 = andCriteria1.filterProduct(list);
        r3.forEach(System.out::println);
        //筛选红圈
        Criteria orCriteria1 = new OrCriteria(c,b);
        List<Product> r4 = orCriteria1.filterProduct(list);
        r4.forEach(System.out::println);
    }
}

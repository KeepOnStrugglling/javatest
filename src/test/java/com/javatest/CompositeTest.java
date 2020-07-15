package com.javatest;

import com.javatest.po.CompositePo.*;

public class CompositeTest {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    private static void test3() {
        Bag bigBag = new Bag("大袋子");
        Bag middleBag = new Bag("中袋子");
        Bag redBag = new Bag("红色小袋子");
        Bag whiteBag = new Bag("白色小袋子");
        Goods goods;
        goods = new Goods("特产",8.9f,2);
        redBag.add(goods);
        goods = new Goods("地图",7.9f,3);
        redBag.add(goods);
        goods = new Goods("香囊",66f,2);
        whiteBag.add(goods);
        goods = new Goods("红茶",110f,3);
        whiteBag.add(goods);
        goods = new Goods("瓷器",460.5f,1);
        middleBag.add(goods);
        middleBag.add(redBag);
        goods = new Goods("运动鞋",218f,1);
        bigBag.add(middleBag);
        bigBag.add(whiteBag);
        bigBag.add(goods);

        bigBag.show();
        System.out.println();
        System.out.println("总价为：" + bigBag.sum());

    }

    private static void test2() {
        BranchSafe root = new BranchSafe("root");
        BranchSafe branch1 = new BranchSafe("branch1");
        LeafSafe leaf1 = new LeafSafe("leaf1");
        LeafSafe leaf2 = new LeafSafe("leaf2");
        LeafSafe leaf3 = new LeafSafe("leaf3");
        root.add(leaf1);
        root.add(branch1);
        branch1.add(leaf2);
        branch1.add(leaf3);
        root.operation();
    }

    private static void test1() {
        Component root = new Branch("root");
        Component branch1 = new Branch("branch1");
        Component leaf1 = new Leaf("leaf1");
        Component leaf2 = new Leaf("leaf2");
        Component leaf3 = new Leaf("leaf3");
        root.add(leaf1);
        root.add(branch1);
        branch1.add(leaf2);
        branch1.add(leaf3);
        root.operation();
    }


}

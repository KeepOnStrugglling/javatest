package com.javatest.domain.FilterPo;

public class Product {
    private String shape;
    private String color;

    @Override
    public String toString() {
        return "Product{" +
                "shape='" + shape + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getShape() {
        return shape;
    }

    public String getColor() {
        return color;
    }

    public Product(String shape, String color) {
        this.shape = shape;
        this.color = color;
    }
}

package com.javatest.domain.FilterPo;

import java.util.ArrayList;
import java.util.List;

public class SquareCriteria implements Criteria {
    @Override
    public List<Product> filterProduct(List<Product> list) {
        List<Product> rList = new ArrayList<>();
        for (Product product : list) {
            if (product.getShape().equals("square")) {
                rList.add(product);
            }
        }
        return rList;
    }
}

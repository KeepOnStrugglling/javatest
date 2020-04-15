package com.javatest.po.FilterPo;

import java.util.ArrayList;
import java.util.List;

public class BlueCriteria implements Criteria {
    @Override
    public List<Product> filterProduct(List<Product> list) {
        List<Product> rList = new ArrayList<>();
        for (Product product : list) {
            if (product.getColor().equals("blue")) {
                rList.add(product);
            }
        }
        return rList;
    }
}

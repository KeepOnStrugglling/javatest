package com.javatest.po.FilterPo;

import java.util.List;

public class OrCriteria implements Criteria {
    private Criteria criteria1;
    private Criteria criteria2;

    public OrCriteria(Criteria criteria1, Criteria criteria2) {
        this.criteria1 = criteria1;
        this.criteria2 = criteria2;
    }

    @Override
    public List<Product> filterProduct(List<Product> list) {
        List<Product> list1 = criteria1.filterProduct(list);
        List<Product> list2 = criteria2.filterProduct(list);
        for (Product product : list2) {
            if (list1.contains(product)) {
                list1.remove(product);
            }
        }
        return list1;
    }
}

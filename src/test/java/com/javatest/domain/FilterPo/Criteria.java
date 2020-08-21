package com.javatest.domain.FilterPo;

import java.util.List;

public interface Criteria {
    List<Product> filterProduct(List<Product> list);
}

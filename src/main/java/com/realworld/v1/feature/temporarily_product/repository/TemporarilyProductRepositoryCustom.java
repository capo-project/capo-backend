package com.realworld.v1.feature.temporarily_product.repository;


import com.realworld.v1.feature.temporarily_product.domain.TemporarilyProduct;

import java.util.List;

public interface TemporarilyProductRepositoryCustom {
    TemporarilyProduct temporarilyProductDetails(Long seq);

    List<TemporarilyProduct> temporarilyProductList(String userId);
}

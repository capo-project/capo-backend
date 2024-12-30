package com.realworld.v1.feature.temporarily_product.service;


import com.realworld.v1.feature.temporarily_product.domain.TemporarilyProduct;

import java.util.List;

public interface TemporarilyProductQueryService {
    TemporarilyProduct temporarilyProductDetails(Long seq);

    List<TemporarilyProduct> temporarilyProductList(String userId);
}

package com.realworld.v1.feature.product.repository;


import com.realworld.v1.feature.product.domain.Product;
import com.realworld.v1.global.category.GroupCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getSearchProductList(Pageable pageable, String search, GroupCategory category, Long seq);

    Product getDetailsProduct(Long seq);
}

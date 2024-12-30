package com.realworld.feature.product.service.product;

import com.realworld.feature.product.domain.Product;
import com.realworld.global.category.GroupCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductQueryService {
    List<Product> getSearchProductList(final Pageable pageable, String search, GroupCategory category, final Long seq);

    Product getDetailsProduct(Long seq);
}

package com.realworld.feature.product.service.product_file;

import com.realworld.feature.product.domain.Product;
import com.realworld.feature.product.domain.ProductFile;

public interface ProductFileCommandService {
    ProductFile save(String imageId, Product product);

    void delete(String username, String imageId);
}

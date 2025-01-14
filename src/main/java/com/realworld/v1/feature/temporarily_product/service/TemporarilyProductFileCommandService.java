package com.realworld.v1.feature.temporarily_product.service;


import com.realworld.v1.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.v1.feature.temporarily_product.domain.TemporarilyProductFile;

public interface TemporarilyProductFileCommandService {
    TemporarilyProductFile save(String imageId, TemporarilyProduct product);

    void delete(String userId, String imageId);
}

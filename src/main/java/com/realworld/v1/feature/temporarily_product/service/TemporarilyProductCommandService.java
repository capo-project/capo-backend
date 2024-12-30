package com.realworld.feature.temporarily_product.service;

import com.realworld.feature.temporarily_product.controller.request.TemporarilyProductGenerationRequest;
import com.realworld.feature.temporarily_product.controller.request.TemporarilyProductUpdateRequest;
import com.realworld.feature.temporarily_product.domain.TemporarilyProduct;
import org.springframework.security.core.userdetails.User;

public interface TemporarilyProductCommandService {
    TemporarilyProduct productTemporarilyGeneration(User user, TemporarilyProductGenerationRequest request);

    TemporarilyProduct update(String userId, TemporarilyProductUpdateRequest request);

    void delete(User user, TemporarilyProduct product);
}

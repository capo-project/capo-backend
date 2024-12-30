package com.realworld.feature.product.service.product;


import com.realworld.feature.product.controller.request.ConvertProductGenerationRequest;
import com.realworld.feature.product.controller.request.ProductGenerationRequest;
import com.realworld.feature.product.controller.request.ProductUpdateRequest;
import com.realworld.feature.product.domain.Product;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface ProductCommandService {

    List<Product> convertProductGeneration(User user, List<ConvertProductGenerationRequest> request);

    Product generation(String userId, ProductGenerationRequest request);

    Product productUpdates(User user, ProductUpdateRequest request);

    void productDelete(User user, Product product);

    void raiseLikeCount(Product product);

    void decreaseLikeCount(Product product);

    void raiseViews(Long productSeq);

}


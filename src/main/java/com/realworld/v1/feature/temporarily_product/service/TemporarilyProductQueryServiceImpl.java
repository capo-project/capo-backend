package com.realworld.feature.temporarily_product.service;

import com.realworld.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.feature.temporarily_product.repository.TemporarilyProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemporarilyProductQueryServiceImpl implements TemporarilyProductQueryService {
    private final TemporarilyProductRepository repository;

    @Override
    public TemporarilyProduct temporarilyProductDetails(Long seq) {
        return repository.temporarilyProductDetails(seq);
    }

    @Override
    public List<TemporarilyProduct> temporarilyProductList(String userId) {
        return repository.temporarilyProductList(userId);
    }
}

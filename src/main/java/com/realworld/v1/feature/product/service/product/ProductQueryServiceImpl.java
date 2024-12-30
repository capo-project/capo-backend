package com.realworld.feature.product.service.product;

import com.realworld.feature.product.domain.Product;
import com.realworld.feature.product.repository.ProductRepository;
import com.realworld.global.category.GroupCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getSearchProductList(Pageable pageable, String search, GroupCategory category, Long seq) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        List<Product> cards = productRepository.getSearchProductList(pageRequest, search, category, seq);

        return cards;
    }

    @Override
    public Product getDetailsProduct(Long seq) {
        return productRepository.getDetailsProduct(seq);
    }

}

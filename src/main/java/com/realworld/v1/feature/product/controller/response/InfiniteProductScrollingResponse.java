package com.realworld.feature.product.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.product.domain.Product;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.config.exception.CustomProductExceptionHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@ToString
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfiniteProductScrollingResponse {
    private final List<Product> products;
    private boolean isNext;
    private Long nextCursor;

    public InfiniteProductScrollingResponse(List<Product> products) {
        this.products = products;
    }

    // 다음 존재 여부 확인
    public void infiniteIsNext(Pageable pageable) {
        this.isNext = products.size() > pageable.getPageSize();
    }

    public void infiniteLastSeq() {
        Optional<Product> product = products.stream().max(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.getProductSeq().compareTo(o1.getProductSeq());
            }
        });
        this.nextCursor = product.orElseThrow(() -> new CustomProductExceptionHandler(ErrorCode.NOT_EXISTS_PRODUCT)).getProductSeq();
    }
}

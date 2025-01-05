package com.realworld.v1.feature.temporarily_product.controller.response;

import com.realworld.v1.feature.temporarily_product.domain.TemporarilyProduct;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TemporarilyProductListResponse {
    List<TemporarilyProduct> products;
}

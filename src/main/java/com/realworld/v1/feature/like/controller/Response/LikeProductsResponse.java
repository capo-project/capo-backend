package com.realworld.v1.feature.like.controller.Response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LikeProductsResponse {
    private Long likeSeq;

    private Product product;
    
    private Member member;
}

package com.realworld.feature.like.controller.Response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.product.domain.Product;
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

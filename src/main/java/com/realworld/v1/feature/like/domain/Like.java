package com.realworld.v1.feature.like.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.realworld.v1.feature.like.controller.Response.LikeProductsResponse;
import com.realworld.v1.feature.like.entity.ProductLikeJpaEntity;
import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Like {
    private Long likeSeq;

    private Member member;

    private Product product;

    public ProductLikeJpaEntity toEntity() {
        return ProductLikeJpaEntity.builder()
                .member(this.member.toEntity())
                .product(this.product.toEntity())
                .build();
    }

    public LikeProductsResponse toResponse() {
        return LikeProductsResponse.builder()
                .likeSeq(this.likeSeq)
                .member(this.member)
                .product(this.product)
                .build();
    }
}

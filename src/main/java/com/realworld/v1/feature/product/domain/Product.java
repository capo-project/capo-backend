package com.realworld.feature.product.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.product.entity.ProductJpaEntity;
import com.realworld.global.category.GroupCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * TODO: Like,Image 추가해야합니다.
 */
@Builder
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    //    private UUID id;
    private Long productSeq;

    private String title;

    private String userId;

    private String content;

    private GroupCategory category;

    private int likeCount;

    private Long price;

    private int views;

    private String thumbnailUrl;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<ProductFile> images;

    private Member member;


    public ProductJpaEntity toEntity() {
        return ProductJpaEntity.builder()
                .userId(this.userId)
                .images(this.images.stream().map(ProductFile::searchToEntity).collect(Collectors.toList()))
                .likeCount(this.likeCount)
                .productSeq(this.productSeq)
                .title(this.title)
                .member(this.member.toEntity())
                .content(this.content)
                .category(this.category)
                .price(this.price)
                .thumbnailUrl(this.thumbnailUrl)
                .build();
    }


}

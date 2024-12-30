package com.realworld.feature.product.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.product.domain.ProductFile;
import com.realworld.global.category.GroupCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductGenerationResponse {
    private Long productSeq;

    private String title;

    private String userId;

    private String content;

    private GroupCategory category;

    private Long price;

    private int views;

    private int likeCount;

    private String thumbnailUrl;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    private List<ProductFile> images;

    private Member member;
}

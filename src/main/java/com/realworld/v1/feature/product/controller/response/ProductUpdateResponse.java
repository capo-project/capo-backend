package com.realworld.feature.product.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.file.domain.File;
import com.realworld.feature.member.domain.Member;
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
public class ProductUpdateResponse {
    private Long seq;

    private String userId;

    private String title;

    private String content;

    private GroupCategory category;

    private Long price;

    private int views;

    private int likeCount;

    private String thumbnailId;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    private List<File> images;

    private Member member;
}

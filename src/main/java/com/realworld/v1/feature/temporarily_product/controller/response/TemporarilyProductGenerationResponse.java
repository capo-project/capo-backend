package com.realworld.feature.temporarily_product.controller.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;
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
public class TemporarilyProductGenerationResponse {

    private Long productSeq;

    private String title;

    private String userId;

    private String content;

    private GroupCategory category;

    private Long price;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    private String thumbnailUrl;

    private List<TemporarilyProductFile> images;

    private Member member;

}


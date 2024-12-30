package com.realworld.feature.product.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.global.category.GroupCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductGenerationRequest {
    @NotNull(message = "제목이 입력되지 않았습니다.")
    private String title;

    @NotNull(message = "내용이 입력되지 않았습니다.")
    private String content;

    @NotNull(message = "카테고리를 입력해주세요.")
    private GroupCategory category;

    @NotNull(message = "가격을 입력해주세요.")
    private Long price;

    @JsonProperty("thumbnail_id")
    @NotNull(message = "썸네일 이미지를 선택해주세요.")
    private String thumbnailUrl;

    @NotNull(message = "사진은 필수 값입니다.")
    private List<String> images;
}

package com.realworld.feature.temporarily_product.controller.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.global.category.GroupCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TemporarilyProductUpdateRequest {
    @NotNull(message = "다시 시도해 주세요.")
    private Long productSeq;

    @NotNull(message = "제목을 입력해 주세요.")
    private String title;

    @NotNull(message = "내용을 입력해 주세요.")
    private String content;

    @NotNull(message = "카테고리가 입력되지 않았습니다.")
    private GroupCategory category;

    @NotNull(message = "가격을 입력하지 않았습니다.")
    private Long price;

    @NotNull(message = "썸네일을 선택해주세요.")
    @JsonProperty("thumbnail_id")
    private String thumbnailId;

    @NotNull(message = "사진은 필수 값입니다.")
    private List<String> images;
}

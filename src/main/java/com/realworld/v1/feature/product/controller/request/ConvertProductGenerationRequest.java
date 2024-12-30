package com.realworld.feature.product.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;
import com.realworld.global.category.GroupCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ConvertProductGenerationRequest {
    @NotNull(message = "요청 값 오류")
    private Long seq;

    @NotNull(message = "요청 값 오류입니다.")
    private GroupCategory category;

    @NotNull(message = "내용이 입력되지 않았습니다.")
    private String content;

    private String hashtag;

    @NotNull(message = "가격이 입력되지 않았습니다.")
    private Long price;

    @NotNull(message = "제목이 입력되지 않았습니다.")
    private String title;

    private Member member;

    @NotEmpty(message = "사진은 필수 입력입니다.")
    private List<TemporarilyProductFile> images;

    @NotNull(message = "썸네일은 필수 입니다.")
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
}

package com.realworld.feature.temporarily_product.domain;

import com.realworld.feature.member.domain.Member;
import com.realworld.feature.temporarily_product.entity.TemporarilyProductJpaEntity;
import com.realworld.global.category.GroupCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
public class TemporarilyProduct {
    private Long productSeq;

    private String title;

    private String userId;

    private String content;

    private GroupCategory category;

    private Long price;

    private String thumbnailUrl;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<TemporarilyProductFile> images;

    private Member member;

    public TemporarilyProductJpaEntity toEntity() {
        return TemporarilyProductJpaEntity.builder()
                .productSeq(this.productSeq)
                .title(this.title)
                .userId(this.userId)
                .images(this.images.stream().map(TemporarilyProductFile::searchToEntity).collect(Collectors.toList()))
                .member(this.member.toEntity())
                .content(this.content)
                .category(this.category)
                .price(this.price)
                .thumbnailUrl(this.thumbnailUrl)
                .build();
    }

}

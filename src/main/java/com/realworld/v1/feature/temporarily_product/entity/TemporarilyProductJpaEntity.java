package com.realworld.feature.temporarily_product.entity;

import com.realworld.feature.member.entity.MemberJpaEntity;
import com.realworld.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.global.category.GroupCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@EqualsAndHashCode
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "temporarily_product")
public class TemporarilyProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_seq")
    private Long productSeq;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private MemberJpaEntity member;

    @OneToMany(mappedBy = "product")
    private List<TemporarilyProductFileJpaEntity> images = new ArrayList<>();

    private String title;

    @Column(length = 50000)
    private String content;

    private Long price;

    @Enumerated(EnumType.STRING)
    private GroupCategory category;

    private String hashtag;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


    public TemporarilyProduct toDomain() {
        return TemporarilyProduct.builder()
                .productSeq(this.productSeq)
                .images(this.images.stream().map(TemporarilyProductFileJpaEntity::toDomain).collect(Collectors.toList()))
                .title(this.title)
                .userId(this.userId)
                .member(this.member.toDomain())
                .content(this.content)
                .price(this.price)
                .category(this.category)
                .thumbnailUrl(this.thumbnailUrl)
                .createdAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public TemporarilyProduct generationToDomain() {
        return TemporarilyProduct.builder()
                .productSeq(this.productSeq)
                .images(this.images.stream().map(TemporarilyProductFileJpaEntity::generationToDomain).collect(Collectors.toList()))
                .title(this.title)
                .userId(this.userId)
                .member(this.member.productToDomain())
                .content(this.content)
                .price(this.price)
                .category(this.category)
                .thumbnailUrl(this.thumbnailUrl)
                .createdAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public TemporarilyProduct updateToDomain() {
        return TemporarilyProduct.builder()
                .productSeq(this.productSeq)
                .images(this.images.stream().map(TemporarilyProductFileJpaEntity::updateToDomain).collect(Collectors.toList()))
                .title(this.title)
                .userId(this.userId)
                .member(this.member.productToDomain())
                .content(this.content)
                .price(this.price)
                .category(this.category)
                .thumbnailUrl(this.thumbnailUrl)
                .createdAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public TemporarilyProduct searchToDomain() {
        return TemporarilyProduct.builder()
                .productSeq(this.productSeq)
                .images(this.images.stream().map(TemporarilyProductFileJpaEntity::searchToDomain).collect(Collectors.toList()))
                .title(this.title)
                .userId(this.userId)
                .member(this.member.productToDomain())
                .content(this.content)
                .price(this.price)
                .category(this.category)
                .thumbnailUrl(this.thumbnailUrl)
                .createdAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

}

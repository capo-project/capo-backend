package com.realworld.feature.product.entity;

import com.realworld.feature.file.entity.ProductFileJpaEntity;
import com.realworld.feature.member.entity.MemberJpaEntity;
import com.realworld.feature.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: ProductJpaEntity Image, Like 좋아요 기능 컬럼 미구현 (추후 개발)
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "product")
public class ProductJpaEntity {

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
    private List<ProductFileJpaEntity> images = new ArrayList<>();

    private String title;

    @ColumnDefault("0")
    @Column(name = "like_count")
    private int likeCount;

    @Column(length = 50000)
    private String content;

    private Long price;

    private String category;

    private String hashtag;

    @ColumnDefault("0")
    private int views;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


    @Builder
    public ProductJpaEntity(Long productSeq, String userId, String title, MemberJpaEntity member, String content, Long price, String category, int views, List<ProductFileJpaEntity> images, LocalDateTime createAt, LocalDateTime modifiedAt) {
//        this.id = id;
        this.productSeq = productSeq;
        this.userId = userId;
        this.title = title;
        this.member = member;
        this.content = content;
        this.price = price;
        this.category = category;
        this.views = views;
        this.images = images;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public Product toDomain() {
        return Product.builder()
//                .id(this.id)
                .productSeq(this.productSeq)
                .images(this.images)
                .title(this.title)
                .userId(this.userId)
                .member(this.member)
                .content(this.content)
                .price(this.price)
                .category(this.category)
                .views(this.views)
                .createDt(this.createAt)
                .regDt(this.modifiedAt)
                .build();
    }

}


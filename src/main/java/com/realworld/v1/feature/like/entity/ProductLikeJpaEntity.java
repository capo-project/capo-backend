package com.realworld.v1.feature.like.entity;

import com.realworld.v1.feature.like.domain.Like;
import com.realworld.v1.feature.member.entity.MemberJpaEntity;
import com.realworld.v1.feature.product.entity.ProductJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Table(name = "product_like")
@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProductLikeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_seq")
    private Long likeSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private MemberJpaEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_seq", referencedColumnName = "product_seq")
    private ProductJpaEntity product;

    public Like toDomain() {
        return Like.builder()
                .likeSeq(this.likeSeq)
                .member(this.member.toDomain())
                .product(this.product.toDomain())
                .build();
    }

    public Like getProductToDomain() {
        return Like.builder()
                .likeSeq(this.likeSeq)
                .member(this.member.productToDomain())
                .product(this.product.likeToDomain())
                .build();
    }
}

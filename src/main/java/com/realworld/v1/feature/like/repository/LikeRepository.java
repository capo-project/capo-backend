package com.realworld.v1.feature.like.repository;


import com.realworld.v1.feature.like.entity.ProductLikeJpaEntity;
import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;
import com.realworld.v1.feature.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<ProductLikeJpaEntity, Long>, LikeRepositoryCustom {

    boolean existsByMemberAndProduct(MemberJpaEntityV1 member, ProductJpaEntity product);

    void deleteByMemberAndProduct(MemberJpaEntityV1 member, ProductJpaEntity product);

    
}

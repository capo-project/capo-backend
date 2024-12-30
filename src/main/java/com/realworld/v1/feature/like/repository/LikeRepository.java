package com.realworld.v1.feature.like.repository;


import com.realworld.v1.feature.like.entity.ProductLikeJpaEntity;
import com.realworld.v1.feature.member.entity.MemberJpaEntity;
import com.realworld.v1.feature.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<ProductLikeJpaEntity, Long>, LikeRepositoryCustom {

    boolean existsByMemberAndProduct(MemberJpaEntity member, ProductJpaEntity product);

    void deleteByMemberAndProduct(MemberJpaEntity member, ProductJpaEntity product);

    
}

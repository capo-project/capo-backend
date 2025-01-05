package com.realworld.v1.feature.like.service;


import com.realworld.v1.feature.like.domain.Like;
import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.product.domain.Product;

public interface LikeCommandService {
    Like save(Like like);

    void deleteByMemberAndProduct(Member member, Product product);
}

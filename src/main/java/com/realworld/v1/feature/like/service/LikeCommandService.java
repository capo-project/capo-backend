package com.realworld.feature.like.service;

import com.realworld.feature.like.domain.Like;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.product.domain.Product;

public interface LikeCommandService {
    Like save(Like like);

    void deleteByMemberAndProduct(Member member, Product product);
}

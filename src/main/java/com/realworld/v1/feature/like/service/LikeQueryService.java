package com.realworld.v1.feature.like.service;


import com.realworld.v1.feature.like.domain.Like;
import com.realworld.v1.feature.member.domain.Member;

import java.util.List;

public interface LikeQueryService {

    boolean existsByMemberAndProduct(Member member, Long product);

    List<Like> findUserProductLikes(Member member);
}

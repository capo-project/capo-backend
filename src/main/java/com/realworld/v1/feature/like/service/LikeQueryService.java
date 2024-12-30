package com.realworld.feature.like.service;


import com.realworld.feature.like.domain.Like;
import com.realworld.feature.member.domain.Member;

import java.util.List;

public interface LikeQueryService {

    boolean existsByMemberAndProduct(Member member, Long product);

    List<Like> findUserProductLikes(Member member);
}

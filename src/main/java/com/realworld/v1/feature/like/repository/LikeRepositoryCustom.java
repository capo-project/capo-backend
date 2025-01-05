package com.realworld.v1.feature.like.repository;

import com.realworld.v1.feature.like.domain.Like;

import java.util.List;

public interface LikeRepositoryCustom {
    List<Like> findUserProductLikes(String userId);
}

package com.realworld.feature.like.repository;

import com.realworld.feature.like.domain.Like;

import java.util.List;

public interface LikeRepositoryCustom {
    List<Like> findUserProductLikes(String userId);
}

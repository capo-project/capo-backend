package com.realworld.feature.like.service;

import com.realworld.feature.like.domain.Like;
import com.realworld.feature.like.repository.LikeRepository;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCommandServiceImpl implements LikeCommandService {

    private final LikeRepository repository;

    @Override
    public Like save(Like like) {
        return repository.save(like.toEntity()).toDomain();
    }

    @Override
    public void deleteByMemberAndProduct(Member member, Product product) {
        repository.deleteByMemberAndProduct(member.toEntity(), product.toEntity());
    }
}

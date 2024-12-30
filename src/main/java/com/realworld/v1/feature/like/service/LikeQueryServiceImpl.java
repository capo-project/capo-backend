package com.realworld.feature.like.service;

import com.realworld.feature.like.domain.Like;
import com.realworld.feature.like.repository.LikeRepository;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.product.entity.ProductJpaEntity;
import com.realworld.feature.product.repository.ProductRepository;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.config.exception.CustomProductExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeQueryServiceImpl implements LikeQueryService {

    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;

    @Override
    public boolean existsByMemberAndProduct(Member member, Long productSeq) {
        ProductJpaEntity entity = productRepository.findById(productSeq).orElseThrow(() -> new CustomProductExceptionHandler(ErrorCode.NOT_EXISTS_PRODUCT));

        return likeRepository.existsByMemberAndProduct(member.toEntity(), entity);
    }

    @Override
    public List<Like> findUserProductLikes(Member member) {

        return likeRepository.findUserProductLikes(member.getUserId());
    }
}

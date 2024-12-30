package com.realworld.feature.temporarily_product.service;

import com.realworld.feature.member.domain.Member;
import com.realworld.feature.member.service.MemberQueryService;
import com.realworld.feature.temporarily_product.controller.request.TemporarilyProductGenerationRequest;
import com.realworld.feature.temporarily_product.controller.request.TemporarilyProductUpdateRequest;
import com.realworld.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.feature.temporarily_product.entity.TemporarilyProductJpaEntity;
import com.realworld.feature.temporarily_product.repository.TemporarilyProductRepository;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.config.exception.CustomProductExceptionHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("temporarilyProductCommandService")
@RequiredArgsConstructor
public class TemporarilyProductCommandServiceImpl implements TemporarilyProductCommandService {
    private final MemberQueryService memberQueryService;
    private final TemporarilyProductRepository repository;

    @Override
    @Transactional
    public TemporarilyProduct productTemporarilyGeneration(User user, TemporarilyProductGenerationRequest request) {
        Member member = memberQueryService.getMemberByUserId(user.getUsername());

        TemporarilyProduct product = TemporarilyProduct.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .userId(member.getUserId())
                .member(member)
                .images(new ArrayList<>())
                .category(request.getCategory())
                .price(request.getPrice())
                .thumbnailUrl(request.getThumbnailUrl())
                .build();

        return repository.save(product.toEntity()).generationToDomain();
    }

    @Override
    @Transactional
    public TemporarilyProduct update(String userId, TemporarilyProductUpdateRequest request) {
        TemporarilyProductJpaEntity entity = repository.findById(request.getProductSeq()).orElseThrow();
        entity.setCategory(request.getCategory());
        entity.setPrice(request.getPrice());
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setThumbnailUrl(request.getThumbnailId());
        return entity.updateToDomain();
    }

    @Override
    public void delete(User user, TemporarilyProduct product) {
        if (!user.getUsername().equals(product.getUserId())) {
            throw new CustomProductExceptionHandler(ErrorCode.NOT_MATCHES_USER_PRODUCT);
        }

        repository.delete(product.toEntity());
    }
}

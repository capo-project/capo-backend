package com.realworld.feature.like.controller;

import com.realworld.feature.like.controller.Response.LikeProductsResponse;
import com.realworld.feature.like.domain.Like;
import com.realworld.feature.like.service.LikeCommandService;
import com.realworld.feature.like.service.LikeQueryService;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.member.service.MemberQueryService;
import com.realworld.feature.product.domain.Product;
import com.realworld.feature.product.service.product.ProductCommandService;
import com.realworld.feature.product.service.product.ProductQueryService;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;

    private final LikeQueryService likeQueryService;

    private final ProductQueryService productQueryService;

    private final ProductCommandService productCommandService;

    private final MemberQueryService memberQueryService;

    /**
     * TODO:: ArgumentResolver를 통해서 Member 가져오기코드 통일화 @Annotation으로 작성하기 , Transactional 전체 걸기 고민하기
     *
     * @param user
     * @param productSeq
     * @return
     */
    @Transactional
    @PostMapping("/{productSeq}")
    public ResponseEntity<ApiResponse<?>> clickLikes(@AuthenticationPrincipal User user, @PathVariable(name = "productSeq") Long productSeq) {

        Member member = memberQueryService.getMemberByUserId(user.getUsername());

        Product product = productQueryService.getDetailsProduct(productSeq);
        Like like = Like.builder()
                .member(member)
                .product(product)
                .build();

        ApiResponse<?> apiResponse = null;

        // 상품에 대한 좋아요 존재여부 판단 및 없을 경우 클릭 좋아요 추가
        if (!likeQueryService.existsByMemberAndProduct(member, productSeq)) {
            productCommandService.raiseLikeCount(product);
            likeCommandService.save(like);
            apiResponse = new ApiResponse<>(null, SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());
        } else { // 좋아요 있는 경우
            productCommandService.decreaseLikeCount(product);
            likeCommandService.deleteByMemberAndProduct(member, product);
            apiResponse = new ApiResponse<>(null, SuccessCode.DELETE_SUCCESS.getStatus(), SuccessCode.DELETE_SUCCESS.getMessage());
        }
        
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LikeProductsResponse>>> likeProducts(@AuthenticationPrincipal User user) {
        Member member = memberQueryService.getMemberByUserId(user.getUsername());

        List<Like> likes = likeQueryService.findUserProductLikes(member);

        if (!likes.isEmpty()) { // 비어있지 않으면 성공로직
            List<LikeProductsResponse> responses = likes.stream().map(Like::toResponse).toList();
            ApiResponse<List<LikeProductsResponse>> apiResponse = new ApiResponse<>(responses, SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

            return ResponseEntity.ok(apiResponse);
        }

        //비어 있어도 오류는 아님 좋아요를 아무것도 안 눌르수 있음.
        return ResponseEntity.ok(new ApiResponse<>(null, SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage()));
    }
}

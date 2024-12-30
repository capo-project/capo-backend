package com.realworld.feature.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.realworld.feature.file.domain.File;
import com.realworld.feature.file.service.FileQueryService;
import com.realworld.feature.product.controller.request.ConvertProductGenerationRequest;
import com.realworld.feature.product.controller.request.ProductGenerationRequest;
import com.realworld.feature.product.controller.request.ProductUpdateRequest;
import com.realworld.feature.product.controller.response.InfiniteProductScrollingResponse;
import com.realworld.feature.product.controller.response.ProductDetailsResponse;
import com.realworld.feature.product.controller.response.ProductGenerationResponse;
import com.realworld.feature.product.controller.response.ProductUpdateResponse;
import com.realworld.feature.product.domain.Product;
import com.realworld.feature.product.domain.ProductFile;
import com.realworld.feature.product.service.product.ProductCommandService;
import com.realworld.feature.product.service.product.ProductQueryService;
import com.realworld.feature.product.service.product_file.ProductFileCommandService;
import com.realworld.global.category.GroupCategory;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class ProductController {
    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;
    private final ProductFileCommandService productFileCommandService;
    private final FileQueryService fileQueryService;


    /**
     * TODO :: TestCode 작성 및 Product , ProductJpaEntity 추가하기..!
     *
     * @param seq
     * @param search
     * @param category
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<InfiniteProductScrollingResponse>> getSearchCardList(@RequestParam(value = "seq", required = false) Long seq, @RequestParam(required = false) String search, @RequestParam(required = false) GroupCategory category, @PageableDefault(size = 10, sort = "seq", direction = Sort.Direction.DESC) Pageable pageable) throws JsonProcessingException {
        List<Product> products = productQueryService.getSearchProductList(pageable, search, category, seq);

        InfiniteProductScrollingResponse scrollingResponse = new InfiniteProductScrollingResponse(products);
        scrollingResponse.infiniteIsNext(pageable); // 다음여부 확인 세팅
        scrollingResponse.infiniteLastSeq();

        ApiResponse<InfiniteProductScrollingResponse> apiResponse = new ApiResponse<>(scrollingResponse, SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());
        // ResponseEntity를 사용하여 ApiResponse 객체를 JSON으로 변환하여 반환
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/temporarily/product")
    public ResponseEntity<ApiResponse<?>> convertProductGeneration(@AuthenticationPrincipal User user, @RequestBody @Valid List<ConvertProductGenerationRequest> requests) {
        List<Product> products = productCommandService.convertProductGeneration(user, requests);

        ApiResponse<?> response = new ApiResponse<>(null, SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());


        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductGenerationResponse>> generation(@AuthenticationPrincipal User user, @RequestBody ProductGenerationRequest request) {
        Product product = productCommandService.generation(user.getUsername(), request);

        List<ProductFile> images = new ArrayList<>();
        request.getImages().forEach(imageId -> images.add(productFileCommandService.save(imageId, product)));

        ProductGenerationResponse response = ProductGenerationResponse.builder()
                .productSeq(product.getProductSeq())
                .title(product.getTitle())
                .userId(product.getUserId())
                .content(product.getContent())
                .category(product.getCategory())
                .price(product.getPrice())
                .views(product.getViews())
                .likeCount(product.getLikeCount())
                .thumbnailUrl(product.getThumbnailUrl())
                .createAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .images(images)
                .member(product.getMember())
                .build();

        ApiResponse<ProductGenerationResponse> apiResponse = new ApiResponse<>(response, SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.INSERT_SUCCESS.getMessage());
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<ProductUpdateResponse>> productUpdates(@AuthenticationPrincipal User user, @RequestBody @Valid ProductUpdateRequest request) {

        Product product = productCommandService.productUpdates(user, request);

        // 새로 추가된 imageIds
        List<String> newImageIds = request.getImages().stream().filter(imageId ->
                !product.getImages().stream().map(ProductFile::getId).map(UUID::toString).toList().contains(imageId)
        ).toList();

        newImageIds.forEach(imageId -> productFileCommandService.save(imageId, product));

        // 제거할 imageIds
        List<String> deleteImageIds = product.getImages().stream().map(ProductFile::getId).map(UUID::toString).filter(imageId -> !request.getImages().contains(imageId)).toList();
        deleteImageIds.forEach(imageId -> productFileCommandService.delete(user.getUsername(), imageId));

        List<File> images = new ArrayList<>();
        Product details = productQueryService.getDetailsProduct(product.getProductSeq());
        details.getImages().forEach(image -> images.add(fileQueryService.getFile(image.getId())));

        ProductUpdateResponse response = ProductUpdateResponse.builder()
                .seq(details.getProductSeq())
                .member(details.getMember())
                .category(details.getCategory())
                .price(details.getPrice())
                .title(details.getTitle())
                .content(details.getContent())
                .views(details.getViews())
                .likeCount(details.getLikeCount())
                .createAt(details.getCreatedAt())
                .modifiedAt(details.getModifiedAt())
                .images(images)
                .build();

        ApiResponse<ProductUpdateResponse> apiResponse = new ApiResponse<>(response, SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());


        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{product_seq}")
    public ResponseEntity<ApiResponse<?>> productDeletes(@AuthenticationPrincipal User user, @PathVariable(value = "product_seq") Long productSeq) {
        Product product = productQueryService.getDetailsProduct(productSeq);

        productCommandService.productDelete(user, product);
        product.getImages().forEach(image -> productFileCommandService.delete(user.getUsername(), String.valueOf(image.getId())));

        ApiResponse<?> productDeleteResponse = new ApiResponse<>(null,
                SuccessCode.DELETE_SUCCESS.getStatus(), SuccessCode.DELETE_SUCCESS.getMessage());

        return ResponseEntity.ok(productDeleteResponse);
    }

    @GetMapping("/{product_seq}")
    public ResponseEntity<ApiResponse<?>> productDetails(@PathVariable(value = "product_seq") Long productSeq) {
        Product product = productQueryService.getDetailsProduct(productSeq);
        ProductDetailsResponse response = ProductDetailsResponse.builder()
                .productSeq(product.getProductSeq())
                .member(product.getMember())
                .likeCount(product.getLikeCount())
                .category(product.getCategory())
                .title(product.getTitle())
                .price(product.getPrice())
                .content(product.getContent())
                .thumbnailUrl(product.getThumbnailUrl())
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .images(product.getImages())
                .build();

        productCommandService.raiseViews(productSeq);

        ApiResponse<ProductDetailsResponse> apiResponse = new ApiResponse<>(response,
                SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

        return ResponseEntity.ok(apiResponse);
    }
}

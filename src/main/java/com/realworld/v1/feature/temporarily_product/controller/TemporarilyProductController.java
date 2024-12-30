package com.realworld.feature.temporarily_product.controller;

import com.realworld.feature.file.domain.File;
import com.realworld.feature.file.service.FileQueryService;
import com.realworld.feature.temporarily_product.controller.request.TemporarilyProductGenerationRequest;
import com.realworld.feature.temporarily_product.controller.request.TemporarilyProductUpdateRequest;
import com.realworld.feature.temporarily_product.controller.response.TemporarilyProductDetailsResponse;
import com.realworld.feature.temporarily_product.controller.response.TemporarilyProductGenerationResponse;
import com.realworld.feature.temporarily_product.controller.response.TemporarilyProductListResponse;
import com.realworld.feature.temporarily_product.controller.response.TemporarilyProductUpdateResponse;
import com.realworld.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;
import com.realworld.feature.temporarily_product.service.TemporarilyProductCommandService;
import com.realworld.feature.temporarily_product.service.TemporarilyProductFileCommandService;
import com.realworld.feature.temporarily_product.service.TemporarilyProductQueryService;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards/temporarily")
public class TemporarilyProductController {
    private final TemporarilyProductCommandService temporarilyProductCommandService;
    private final TemporarilyProductFileCommandService temporarilyProductFileCommandService;
    private final TemporarilyProductQueryService temporarilyProductQueryService;
    private final FileQueryService fileQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<TemporarilyProductGenerationResponse>> temporarilyProductGeneration(@AuthenticationPrincipal User user, @RequestBody @Valid TemporarilyProductGenerationRequest request) throws IOException {

        TemporarilyProduct product = temporarilyProductCommandService.productTemporarilyGeneration(user, request);

        List<TemporarilyProductFile> images = new ArrayList<>();

        if (!request.getImages().isEmpty())
            request.getImages().forEach(imageId -> images.add(temporarilyProductFileCommandService.save(imageId, product)));

        TemporarilyProductGenerationResponse response = TemporarilyProductGenerationResponse.builder()
                .productSeq(product.getProductSeq())
                .title(product.getTitle())
                .userId(product.getUserId())
                .content(product.getContent())
                .category(product.getCategory())
                .price(product.getPrice())
                .thumbnailUrl(product.getThumbnailUrl())
                .createAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .images(images)
                .member(product.getMember())
                .build();

        ApiResponse<TemporarilyProductGenerationResponse> apiResponse = new ApiResponse<>(response, SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.INSERT_SUCCESS.getMessage());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TemporarilyProductListResponse>> temporarilyProductList(@AuthenticationPrincipal User user) {
        List<TemporarilyProduct> products = temporarilyProductQueryService.temporarilyProductList(user.getUsername());

        TemporarilyProductListResponse response = TemporarilyProductListResponse.builder()
                .products(products)
                .build();

        ApiResponse<TemporarilyProductListResponse> apiResponse = new ApiResponse<>(response, SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<TemporarilyProductUpdateResponse>> temporarilyProductUpdate(@AuthenticationPrincipal User user, @RequestBody @Valid TemporarilyProductUpdateRequest request) {
        TemporarilyProduct product = temporarilyProductCommandService.update(user.getUsername(), request);

        List<String> newImageIds = request.getImages().stream().filter(
                imageId -> !product.getImages().stream().map(TemporarilyProductFile::getId).map(UUID::toString).toList().contains(imageId)
        ).toList();

        newImageIds.forEach(imageId -> temporarilyProductFileCommandService.save(imageId, product));

        List<String> deleteImageIds = product.getImages().stream().map(TemporarilyProductFile::getId).map(UUID::toString).filter(imageId ->
                !request.getImages().contains(imageId)).toList();

        deleteImageIds.forEach(imageId -> temporarilyProductFileCommandService.delete(user.getUsername(), imageId));

        List<File> images = new ArrayList<>();
        TemporarilyProduct details = temporarilyProductQueryService.temporarilyProductDetails(product.getProductSeq());
        details.getImages().forEach(image -> images.add(fileQueryService.getFile(image.getId())));

        TemporarilyProductUpdateResponse response = TemporarilyProductUpdateResponse.builder()
                .seq(details.getProductSeq())
                .member(details.getMember())
                .category(details.getCategory())
                .title(details.getTitle())
                .thumbnailUrl(details.getThumbnailUrl())
                .content(details.getContent())
                .createAt(details.getCreatedAt())
                .modifiedAt(details.getModifiedAt())
                .images(images)
                .build();

        ApiResponse<TemporarilyProductUpdateResponse> apiResponse = new ApiResponse<>(response, SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());


        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{temporarily_product_seq}")
    public ResponseEntity<ApiResponse<?>> temporarilyProductDeletes(@AuthenticationPrincipal User user, @PathVariable(value = "temporarily_product_seq") Long seq) {
        TemporarilyProduct product = temporarilyProductQueryService.temporarilyProductDetails(seq);
        temporarilyProductCommandService.delete(user, product);
        product.getImages().stream().map(TemporarilyProductFile::getId).forEach(imageId -> temporarilyProductFileCommandService.delete(user.getUsername(), String.valueOf(imageId)));

        ApiResponse<?> response = new ApiResponse<>(null,
                SuccessCode.DELETE_SUCCESS.getStatus(), SuccessCode.DELETE_SUCCESS.getMessage());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{temporarily_product_seq}")
    public ResponseEntity<ApiResponse<TemporarilyProductDetailsResponse>> details(@PathVariable(value = "temporarily_product_seq") Long seq) {
        TemporarilyProduct product = temporarilyProductQueryService.temporarilyProductDetails(seq);

        List<File> images = new ArrayList<>();
        product.getImages().forEach(imageId -> images.add(fileQueryService.getFile(imageId.getId())));

        TemporarilyProductDetailsResponse response = TemporarilyProductDetailsResponse.builder()
                .temporarilyProductSeq(product.getProductSeq())
                .member(product.getMember())
                .category(product.getCategory())
                .title(product.getTitle())
                .content(product.getContent())
                .thumbnailUrl(product.getThumbnailUrl())
                .createAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .images(images)
                .build();


        ApiResponse<TemporarilyProductDetailsResponse> apiResponse = new ApiResponse<>(response, SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

        return ResponseEntity.ok(apiResponse);
    }
}

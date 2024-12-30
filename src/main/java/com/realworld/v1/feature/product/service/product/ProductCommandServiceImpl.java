package com.realworld.feature.product.service.product;

import com.realworld.feature.member.domain.Member;
import com.realworld.feature.member.service.MemberQueryService;
import com.realworld.feature.product.controller.request.ConvertProductGenerationRequest;
import com.realworld.feature.product.controller.request.ProductGenerationRequest;
import com.realworld.feature.product.controller.request.ProductUpdateRequest;
import com.realworld.feature.product.domain.Product;
import com.realworld.feature.product.domain.ProductFile;
import com.realworld.feature.product.entity.ProductJpaEntity;
import com.realworld.feature.product.repository.ProductRepository;
import com.realworld.feature.product.service.product_file.ProductFileCommandService;
import com.realworld.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;
import com.realworld.feature.temporarily_product.service.TemporarilyProductCommandService;
import com.realworld.feature.temporarily_product.service.TemporarilyProductFileQueryService;
import com.realworld.feature.temporarily_product.service.TemporarilyProductQueryService;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.config.exception.CustomProductExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductRepository repository;

    private final ProductFileCommandService productFileCommandService;

    private final ProductQueryService productQueryService;

    private final TemporarilyProductFileQueryService temporarilyProductFileQueryService;

    private final TemporarilyProductCommandService temporarilyProductCommandService;

    private final TemporarilyProductQueryService temporarilyProductQueryService;

    private final MemberQueryService memberQueryService;

    @Override
    @Transactional
    public List<Product> convertProductGeneration(User user, List<ConvertProductGenerationRequest> requests) {

        List<Product> products = new ArrayList<>();
        Member member = memberQueryService.getMemberByUserId(user.getUsername());

        for (ConvertProductGenerationRequest request : requests) {

            Product data = Product.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .userId(user.getUsername())
                    .member(member)
                    .category(request.getCategory())
                    .price(request.getPrice())
                    .images(new ArrayList<>())
                    .thumbnailUrl(request.getThumbnailUrl())
                    .build();

            Product product = repository.save(data.toEntity()).generationToDomain();

            List<ProductFile> dataImages = request.getImages().stream().map(image -> temporarilyProductFileQueryService.temporarilyProductFileDetails(String.valueOf(image.getId()))).map(TemporarilyProductFile::convertProductFile).toList();

            List<ProductFile> images = new ArrayList<>();
            dataImages.forEach(image -> images.add(productFileCommandService.save(String.valueOf(image.getId()), product)));

            TemporarilyProduct temporarilyProduct = temporarilyProductQueryService.temporarilyProductDetails(request.getSeq());
            temporarilyProductCommandService.delete(user, temporarilyProduct);

            products.add(productQueryService.getDetailsProduct(product.getProductSeq()));

            products.add(Product.builder()
                    .productSeq(product.getProductSeq())
                    .views(product.getViews())
                    .price(product.getPrice())
                    .content(product.getContent())
                    .thumbnailUrl(product.getThumbnailUrl())
                    .title(product.getTitle())
                    .createdAt(product.getCreatedAt())
                    .build());
        }


        return products;
    }

    @Override
    public Product generation(String userId, ProductGenerationRequest request) {
        Member member = memberQueryService.getMemberByUserId(userId);

        Product product = Product.builder()
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
    public Product productUpdates(User user, ProductUpdateRequest request) {
        ProductJpaEntity findProduct = repository.findById(request.getProductSeq()).orElseThrow(() -> new CustomProductExceptionHandler(ErrorCode.NOT_EXISTS_PRODUCT));

        findProduct.setCategory(request.getCategory());
        findProduct.setContent(request.getContent());
        findProduct.setPrice(request.getPrice());
        findProduct.setTitle(request.getTitle());
        findProduct.setThumbnailUrl(request.getThumbnailId());
        return findProduct.updateToDomain();
    }

    @Override
    public void productDelete(User user, Product product) {
        if (!user.getUsername().equals(product.getUserId())) {
            throw new CustomProductExceptionHandler(ErrorCode.NOT_MATCHES_USER_PRODUCT);
        }

        repository.delete(product.toEntity());
    }

    @Transactional
    @Override
    public void raiseLikeCount(Product product) {
        ProductJpaEntity data = repository.findById(product.getProductSeq()).orElseThrow(() -> new CustomProductExceptionHandler(ErrorCode.NOT_EXISTS_PRODUCT));
        data.setLikeCount(data.getLikeCount() + 1);
    }

    @Override
    public void decreaseLikeCount(Product product) {
        ProductJpaEntity data = repository.findById(product.getProductSeq()).orElseThrow(() -> new CustomProductExceptionHandler(ErrorCode.NOT_EXISTS_PRODUCT));
        data.setLikeCount(data.getLikeCount() - 1);
    }

    @Override
    @Transactional
    public void raiseViews(Long productSeq) {
        ProductJpaEntity product = repository.findById(productSeq).orElseThrow(() -> new CustomProductExceptionHandler(ErrorCode.NOT_EXISTS_PRODUCT));
        product.setViews(product.getViews() + 1);
    }
}

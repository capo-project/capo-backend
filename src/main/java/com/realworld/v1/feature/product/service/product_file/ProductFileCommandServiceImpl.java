package com.realworld.v1.feature.product.service.product_file;

import com.realworld.v1.feature.file.service.StorageService;
import com.realworld.v1.feature.product.domain.Product;
import com.realworld.v1.feature.product.domain.ProductFile;
import com.realworld.v1.feature.product.repository.ProductFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ProductFileCommandServiceImpl implements ProductFileCommandService {
    private final ProductFileRepository productFileRepository;
    private final StorageService cloudStorageService;


    @Override
    public ProductFile save(String imageId, Product product) {
        ProductFile productFile = ProductFile.builder()
                .id(UUID.fromString(imageId))
                .userId(product.getUserId())
                .product(product)
                .build();

        return productFileRepository.save(productFile.toEntity()).generationToDomain();
    }

    @Override
    public void delete(String username, String imageId) {
        cloudStorageService.delete(username, imageId);
        productFileRepository.deleteById(UUID.fromString(imageId));
    }
}

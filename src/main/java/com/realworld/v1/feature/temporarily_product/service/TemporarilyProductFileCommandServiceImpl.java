package com.realworld.feature.temporarily_product.service;

import com.realworld.feature.file.service.StorageService;
import com.realworld.feature.temporarily_product.domain.TemporarilyProduct;
import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;
import com.realworld.feature.temporarily_product.repository.TemporarilyProductFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("temporarilyFileCommandService")
@RequiredArgsConstructor
public class TemporarilyProductFileCommandServiceImpl implements TemporarilyProductFileCommandService {

    private final TemporarilyProductFileRepository repository;
    private final StorageService cloudStorageService;

    @Override
    public TemporarilyProductFile save(String imageId, TemporarilyProduct product) {
        TemporarilyProductFile file = TemporarilyProductFile.builder()
                .id(UUID.fromString(imageId))
                .userId(product.getUserId())
                .product(product)
                .build();

        return repository.save(file.toEntity()).generationToDomain();
    }

    @Override
    public void delete(String userId, String imageId) {
        cloudStorageService.delete(userId, imageId);
        repository.deleteById(UUID.fromString(imageId));
    }
}

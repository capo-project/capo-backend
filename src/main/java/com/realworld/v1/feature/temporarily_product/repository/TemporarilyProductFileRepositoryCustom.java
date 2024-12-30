package com.realworld.feature.temporarily_product.repository;

import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;

import java.util.UUID;

public interface TemporarilyProductFileRepositoryCustom {
    TemporarilyProductFile getFileDetails(UUID id);
}

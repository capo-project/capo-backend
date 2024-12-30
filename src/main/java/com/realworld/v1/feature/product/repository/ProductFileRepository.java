package com.realworld.feature.product.repository;

import com.realworld.feature.product.entity.ProductFileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductFileRepository extends JpaRepository<ProductFileJpaEntity, UUID> {
}

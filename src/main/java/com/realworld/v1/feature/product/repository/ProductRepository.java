package com.realworld.feature.product.repository;

import com.realworld.feature.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductJpaEntity, Long>, ProductRepositoryCustom {

}

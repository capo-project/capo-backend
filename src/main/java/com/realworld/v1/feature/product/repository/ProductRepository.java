package com.realworld.v1.feature.product.repository;


import com.realworld.v1.feature.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductJpaEntity, Long>, ProductRepositoryCustom {

}

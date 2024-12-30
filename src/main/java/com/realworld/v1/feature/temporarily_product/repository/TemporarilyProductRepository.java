package com.realworld.feature.temporarily_product.repository;

import com.realworld.feature.temporarily_product.entity.TemporarilyProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TemporarilyProductRepository extends JpaRepository<TemporarilyProductJpaEntity, Long>, TemporarilyProductRepositoryCustom {
}

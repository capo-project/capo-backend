package com.realworld.feature.temporarily_product.repository;

import com.realworld.feature.temporarily_product.entity.TemporarilyProductFileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TemporarilyProductFileRepository extends JpaRepository<TemporarilyProductFileJpaEntity, UUID>, TemporarilyProductFileRepositoryCustom {

}

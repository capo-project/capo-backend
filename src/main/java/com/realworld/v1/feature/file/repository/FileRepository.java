package com.realworld.v1.feature.file.repository;

import com.realworld.v1.feature.file.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileJpaEntity, UUID> {

}

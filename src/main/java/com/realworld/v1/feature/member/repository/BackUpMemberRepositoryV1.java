package com.realworld.v1.feature.member.repository;

import com.realworld.v1.feature.member.entity.BackUpMemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackUpMemberRepositoryV1 extends JpaRepository<BackUpMemberJpaEntity, Long> {

}

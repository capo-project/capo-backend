package com.realworld.feature.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realworld.feature.member.entity.MemberJpaEntity;

public interface MemberRepository extends JpaRepository<MemberJpaEntity, String>, MemberRepositoryCustom{
    MemberJpaEntity findByUserId(String userId);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserId(String userId);
}

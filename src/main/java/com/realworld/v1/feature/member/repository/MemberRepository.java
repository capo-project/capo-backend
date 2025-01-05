package com.realworld.v1.feature.member.repository;


import com.realworld.v1.feature.member.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberJpaEntity, String>, MemberRepositoryCustom {
    MemberJpaEntity findByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserId(String userId);

}

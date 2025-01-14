package com.realworld.v1.feature.member.repository;


import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryV1 extends JpaRepository<MemberJpaEntityV1, String>, MemberRepositoryCustomV1 {
    MemberJpaEntityV1 findByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserId(String userId);

}

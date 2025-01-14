package com.realworld.v1.feature.member.repository;


import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberJpaEntityV1, String>, MemberRepositoryCustom {
    MemberJpaEntityV1 findByUserId(String userId);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserId(String userId);

}

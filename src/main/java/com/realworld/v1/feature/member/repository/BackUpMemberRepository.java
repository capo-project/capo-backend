package com.realworld.feature.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realworld.feature.member.entity.BackUpMemberJpaEntity;

public interface BackUpMemberRepository extends JpaRepository<BackUpMemberJpaEntity, Long> {

}

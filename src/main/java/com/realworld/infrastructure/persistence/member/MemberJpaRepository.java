package com.realworld.infrastructure.persistence.member;

import com.realworld.feature.member.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberJpaRepository extends CrudRepository<Member, String> {

    @Query("SELECT m FROM Member m WHERE m.memberProfile.userEmail = :userEmail")
    Optional<Member> findByUserEmail(String userEmail);

}

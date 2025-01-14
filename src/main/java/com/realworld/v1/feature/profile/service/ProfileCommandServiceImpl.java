package com.realworld.v1.feature.profile.service;

import com.realworld.feature.profile.controller.request.UpdateProfileRequest;
import com.realworld.v1.feature.file.domain.FileV1;
import com.realworld.v1.feature.file.service.CloudStorageService;
import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;
import com.realworld.v1.feature.member.repository.MemberRepositoryV1;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomMemberExceptionHandlerV1;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final MemberRepositoryV1 repository;
    private final CloudStorageService cloudStorageService;

    @Transactional
    @Override
    public Member updateProfile(UpdateProfileRequest request, String userId) {

        MemberJpaEntityV1 member = repository.findById(userId).orElseThrow(() -> new CustomMemberExceptionHandlerV1(ErrorCode.NOT_EXISTS_USERID));

        member.setNickname(request.getNickname());
        if (member.getContent() != null) member.setContent(request.getContent());

        return member.toDomain();
    }

    @Transactional
    @Override
    public Member updateProfileImage(String userId, FileV1 fileV1) {
        MemberJpaEntityV1 member = repository.findById(userId).orElseThrow(() -> new CustomMemberExceptionHandlerV1(ErrorCode.NOT_EXISTS_USERID));

        cloudStorageService.delete(userId, String.valueOf(member.getFile().getId())); // 해당 버킷 파일 삭제
        member.setFile(fileV1.toEntity()); // 파일 변경

        return member.toDomain();
    }
}

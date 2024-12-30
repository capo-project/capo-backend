package com.realworld.feature.member.service;


import com.realworld.feature.auth.Authority;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.member.entity.BackUpMemberJpaEntity;
import com.realworld.feature.member.entity.MemberJpaEntity;
import com.realworld.feature.member.repository.BackUpMemberRepository;
import com.realworld.feature.member.repository.MemberRepository;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.code.ResultErrorMsgCode;
import com.realworld.global.config.exception.CustomLoginExceptionHandler;
import com.realworld.global.config.exception.CustomMemberExceptionHandler;
import com.realworld.global.utils.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository repository;
    private final BackUpMemberRepository backRepository;

    @Override
    public Member saveMember(Member member) {
        if (!CommonUtil.passwordValidationCheck(member.getPassword())) {
            throw new CustomMemberExceptionHandler(ErrorCode.PASSWORD_REQUEST_ERROR);
        }
        if (!CommonUtil.userIdValidationCheck(member.getUserId())) {
            throw new CustomMemberExceptionHandler(ErrorCode.VALIDATION_USERID_ERROR);
        }

        Member registMember = Member.builder()
                .userId(member.getUserId())
                .password(passwordEncoder.encode(member.getPassword()))
                .phoneNumber(member.getPhoneNumber())
                .userEmail(member.getUserEmail())
                .nickname(CommonUtil.createNickname())
                .file(member.getFile())
                .delYn("N")
                .authority(Authority.ROLE_USER)
                .build();

        // repository 예외
        if (repository.existsByUserId(registMember.getUserId()) || repository.existsByUserEmail(registMember.getUserEmail())) {
            throw new CustomLoginExceptionHandler(ResultErrorMsgCode.LOGIN_DUPLICATION_ERROR.getMsg(), ErrorCode.LOGIN_DUPLICATION_ERROR);
        }
        return repository.save(registMember.toEntity()).toDomain();
    }

    @Transactional
    @Override
    public void remove(String userId, String password) {
        Member targetMember = repository.findByUserId(userId).toDomain();

        Member member = Member.builder()
                .userId(targetMember.getUserId())
                .authority(targetMember.getAuthority())
                .phoneNumber(targetMember.getPhoneNumber())
                .userEmail(targetMember.getUserEmail())
                .password(targetMember.getPassword())
                .nickname(targetMember.getNickname())
                .phoneNumber(targetMember.getPhoneNumber())
                .build();

        if (passwordEncoder.matches(password, member.getPassword())) {
            repository.delete(member.toEntity()); // Member 삭제

            BackUpMemberJpaEntity entity = new BackUpMemberJpaEntity();
            entity.memberConvertBackupEntity(member);

            backRepository.save(entity);
        } else {
            throw new CustomMemberExceptionHandler(ErrorCode.NOT_EQUAL_PASSWORD);
        }

    }

    @Transactional
    @Override
    public long updatePassword(Member member) {
        String currentPassword = member.getCurrentPassword();
        String newPassword = member.getNewPassword();


        if (StringUtils.isNotEmpty(member.getUserEmail())) {
            MemberJpaEntity memberEntity = MemberJpaEntity.builder()
                    .userEmail(member.getUserEmail())
                    .build();

            member = repository.findByUserEmail(memberEntity).toDomain();
            if (CommonUtil.isEmpty(member)) throw new CustomMemberExceptionHandler(ErrorCode.NOT_EXISTS_EMAIL);
        }

        if (StringUtils.isNotEmpty(member.getUserId())) {
            member = repository.findByUserId(member.getUserId()).toDomain();
            if (CommonUtil.isEmpty(member)) throw new CustomMemberExceptionHandler(ErrorCode.NOT_EXISTS_USERID);
        }

        if (StringUtils.isNotEmpty(currentPassword)
                && !passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new CustomMemberExceptionHandler(ErrorCode.NOT_EQUAL_PASSWORD);
        }

        Member targetMember = Member.builder()
                .userId(member.getUserId())
                .password(passwordEncoder.encode(newPassword))
                .build();

        return repository.updatePassword(targetMember.toEntity());
    }

    @Override
    public long updateEmail(Member member) {
        return repository.updateEmail(member.toEntity());
    }
}

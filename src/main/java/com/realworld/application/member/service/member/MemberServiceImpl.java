package com.realworld.application.member.service.member;

import com.realworld.application.member.port.MemberRepository;
import com.realworld.common.exception.CustomMemberExceptionHandler;
import com.realworld.common.holder.date.DateTimeHolderImpl;
import com.realworld.common.holder.nickname.NicknameGeneratorHolderImpl;
import com.realworld.common.holder.password.PasswordEncodeHolderImpl;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member signUp(final SignUpRequest request) {

        if(repository.findByUserId(request.userId()).isPresent()){
            throw new CustomMemberExceptionHandler(ExceptionResponseCode.DUPLICATION_USERID_ERROR);
        }

        final Member member = Member.createMember(request, new DateTimeHolderImpl(), new NicknameGeneratorHolderImpl());

        return repository.save(member.passwordEncode(new PasswordEncodeHolderImpl(passwordEncoder)));
    }

    @Override
    @Transactional(readOnly = true)
    public void validateUserIdDuplicate(final String userId) {
        if(repository.findByUserId(userId).isPresent()){
            throw new CustomMemberExceptionHandler(ExceptionResponseCode.DUPLICATION_USERID_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Member findById(String id) {
        return repository.findByUserId(id).orElseThrow(() -> new CustomMemberExceptionHandler(ExceptionResponseCode.NOT_EXISTS_USERID));
    }

}

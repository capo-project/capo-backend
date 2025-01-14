package com.realworld.application.member.service;

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

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(final SignUpRequest request) {

        validateUserIdDuplicate(request.userId());

        final Member member = Member.createMember(request, new DateTimeHolderImpl(), new NicknameGeneratorHolderImpl());

        repository.save(member.passwordEncode(new PasswordEncodeHolderImpl(passwordEncoder)));
    }

    @Override
    public void validateUserIdDuplicate(final String userId) {
        if(repository.findByUserId(userId).isPresent()){
            throw new CustomMemberExceptionHandler(ExceptionResponseCode.DUPLICATION_USERID_ERROR);
        }
    }

}

package com.realworld.application.member.service;

import com.realworld.application.member.port.MemberRepository;
import com.realworld.common.exception.CustomMemberExceptionHandler;
import com.realworld.common.holder.date.DateTimeHolderImpl;
import com.realworld.common.holder.nickname.NicknameGeneratorHolderImpl;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Override
    public void signUp(SignUpRequest request) {

        if(repository.findByUserId(request.userId()).isPresent()){
            throw new CustomMemberExceptionHandler(ExceptionResponseCode.DUPLICATION_USERID_ERROR);
        }

        final Member member = Member.createMember(request, new DateTimeHolderImpl(), new NicknameGeneratorHolderImpl());

        repository.save(member);
    }


}

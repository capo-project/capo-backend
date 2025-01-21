package com.realworld.application.member.service.member;

import com.realworld.application.auth.jwt.port.TokenRepository;
import com.realworld.application.auth.jwt.service.JwtService;
import com.realworld.application.member.port.MemberRepository;
import com.realworld.common.exception.custom.CustomMemberExceptionHandler;
import com.realworld.common.holder.date.DateTimeHolderImpl;
import com.realworld.common.holder.nickname.NicknameGeneratorHolderImpl;
import com.realworld.common.holder.password.PasswordEncodeHolderImpl;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.auth.jwt.Token;
import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.request.LoginRequest;
import com.realworld.web.member.payload.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public Member signUp(final SignUpRequest request) {
        if(repository.findByUserId(request.userId()).isPresent()){
            throw new CustomMemberExceptionHandler(ErrorCode.DUPLICATION_USERID_ERROR);
        }

        final Member member = Member.createMember(request, new DateTimeHolderImpl(), new NicknameGeneratorHolderImpl());

        return repository.save(member.passwordEncode(new PasswordEncodeHolderImpl(passwordEncoder)));
    }

    @Override
    @Transactional(readOnly = true)
    public void validateUserIdDuplicate(final String userId) {
        if(repository.findByUserId(userId).isPresent()){
            throw new CustomMemberExceptionHandler(ErrorCode.DUPLICATION_USERID_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Member findById(String id) {
        return repository.findByUserId(id).orElseThrow(() -> new CustomMemberExceptionHandler(ErrorCode.NOT_EXISTS_USERID));
    }

    @Override
    @Transactional
    public String login(final LoginRequest loginRequest) {
        final Member member = repository.findByUserId(loginRequest.userId()).orElseThrow(() -> new CustomMemberExceptionHandler(ErrorCode.NOT_EXISTS_USERID));
        member.isValidatePassword(member.getPassword());
        String accessToken = jwtService.generateAccessToken(member);
        tokenRepository.save(new Token(member.getUserId(),accessToken));
        return accessToken;
    }

    @Override
    public void logout(String userId) {
        Member member = repository.findByUserId(userId).orElseThrow(() -> new CustomMemberExceptionHandler(ErrorCode.NOT_EXISTS_USERID));
        jwtService.logout(member);
    }

}

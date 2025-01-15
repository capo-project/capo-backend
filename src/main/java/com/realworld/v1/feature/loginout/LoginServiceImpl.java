package com.realworld.v1.feature.loginout;

import com.realworld.v1.feature.member.domain.Member;
import com.realworld.v1.feature.member.service.MemberQueryService;
import com.realworld.v1.feature.token.domain.Token;
import com.realworld.v1.feature.token.service.TokenCommandService;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomLoginExceptionHandlerV1;
import com.realworld.v1.global.config.jwt.JwtTokenProviderV1;
import com.realworld.v1.global.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberQueryService memberQueryService;
    private final PasswordEncoder passwordEncoder;
    private final TokenCommandService tokenCommandService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProviderV1 jwtTokenProviderV1;

    @Override
    public Token loginAndGetToken(Member member) {
        String userId = member.getUserId();
        String password = member.getPassword();

        Member findMember = memberQueryService.findMemberByUserId(userId).orElseThrow(() -> new CustomLoginExceptionHandlerV1(ErrorCode.NOT_EXISTS_USERID));
        
        if (CommonUtil.isEmpty(findMember))
            throw new CustomLoginExceptionHandlerV1(ErrorCode.NOT_EXISTS_USERID);

        //비밀번호가 불일치할 경우
        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new CustomLoginExceptionHandlerV1(ErrorCode.LOGIN_REQUEST_ERROR);
        }

        // 받아온 유저네임과 패스워드를 이용해 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, password);

        // authenticationToken 객체를 통해 Authentication 생성
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        Token token = jwtTokenProviderV1.createToken(authentication);
        token.setUserId(userId);

        Token savedToken = tokenCommandService.saveToken(token);
        savedToken.setNickname(findMember.getNickname());

        return savedToken;
    }
}

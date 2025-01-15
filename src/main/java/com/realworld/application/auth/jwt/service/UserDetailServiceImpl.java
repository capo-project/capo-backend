package com.realworld.application.auth.jwt.service;

import com.realworld.application.member.port.MemberRepository;
import com.realworld.common.exception.CustomMemberExceptionHandler;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.feature.auth.jwt.CustomUserDetails;
import com.realworld.feature.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String userId) throws UsernameNotFoundException {
        final Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomMemberExceptionHandler(ExceptionResponseCode.NOT_EXISTS_USERID));

        return new CustomUserDetails(member);
    }

}

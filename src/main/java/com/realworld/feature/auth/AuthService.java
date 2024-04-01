package com.realworld.feature.auth;

import com.realworld.global.code.ErrorCode;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.config.exception.CustomJwtExceptionHandler;
import com.realworld.global.config.jwt.JwtTokenProvider;
import com.realworld.global.response.ApiResponse;
import com.realworld.feature.member.repository.LoadMemberPort;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.token.LoadTokenPort;
import com.realworld.feature.token.TokenQueryService;
import com.realworld.feature.token.Token;
import com.realworld.feature.token.TokenDTO;
import com.realworld.feature.token.entity.TokenJpaEntity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements TokenQueryService, UserDetailsService {
    private final LoadMemberPort loadMemberPort;
    private final LoadTokenPort loadTokenPort;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : {} ",username);

        return  loadMemberPort.findByUserId(username)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));

    }

    // DB 에 User 값이 존재하면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());
        log.info("Collections.singleton : {}", Collections.singleton(grantedAuthority));
        log.info("member getUserId : {} ", member.getUserId());
        log.info("member getUserEmail : {}", member.getUserEmail());
        log.info("member getPassword : {} ", member.getPassword());
        return new User(
                String.valueOf(member.getUserId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
    @Transactional
    @Override
    public ResponseEntity reissue(TokenDTO tokenDto) {
        if(!jwtTokenProvider.validateToken(tokenDto.getRefreshToken())){
            throw new CustomJwtExceptionHandler(ErrorCode.JWT_TOKEN_REQUEST_ERROR);
        }

        // 2. Access Token에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenDto.getAccessToken());
        TokenDTO target =  jwtTokenProvider.createToken(authentication);

        Optional<TokenJpaEntity> getToken = loadTokenPort.findByUserId(authentication.getName());
        getToken.ifPresent(value ->{
            value.setAccessToken(target.getAccessToken());
            value.setRefreshToken(target.getRefreshToken());
        });


        log.info("token AccessToken {}", getToken.get().getAccessToken());
        log.info("token RefreshToken {}", getToken.get().getRefreshToken());
        log.info("authentication {}", authentication.getName());
        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        Token token = Token.builder()
                .userId(authentication.getName())
                .accessToken(target.getAccessToken())
                .refreshToken(target.getRefreshToken())
                .build();

        return new ResponseEntity(ApiResponse.builder()
                .result(token)
                .resultCode(SuccessCode.UPDATE_SUCCESS.getStatus())
                .resultMsg(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build(), HttpStatus.OK);
    }

    @Override
    public void deleteToken(String userId) {
        loadTokenPort.deleteToken(userId);
    }
}
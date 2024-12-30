package com.realworld.feature.loginout;

import com.realworld.feature.member.domain.Member;
import com.realworld.feature.token.controller.response.TokenResponse;
import com.realworld.feature.token.domain.Token;
import com.realworld.feature.token.service.TokenCommandService;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class LoginOutController {
    private final LoginService loginService;
    private final TokenCommandService tokenCommandService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        Member member = Member.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .build();

        Token token = loginService.loginAndGetToken(member);

        TokenResponse response = TokenResponse.builder()
                .userId(token.getUserId())
                .grantType(token.getGrantType())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .nickname(token.getNickname())
                .build();

        ApiResponse<TokenResponse> tokenApiResponse = new ApiResponse<>(response,
                SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

        return ResponseEntity.ok(tokenApiResponse);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(@AuthenticationPrincipal User user) {
        tokenCommandService.deleteToken(user.getUsername());

        ApiResponse<Token> tokenApiResponse = new ApiResponse<>(null,
                200, "로그아웃 하였습니다.");

        return ResponseEntity.ok(tokenApiResponse);
    }
}

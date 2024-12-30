package com.realworld.feature.token.controller;

import com.realworld.feature.token.controller.request.ReissueRequest;
import com.realworld.feature.token.controller.response.ReissueTokenResponse;
import com.realworld.feature.token.domain.Token;
import com.realworld.feature.token.service.TokenCommandService;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TokenController {

    private final TokenCommandService tokenCommandService;

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<ReissueTokenResponse>> reissue(HttpServletRequest request) {
        String accessToken = request.getHeader("AccessToken");
        String refreshToken = request.getHeader("RefreshToken");

        ReissueRequest reissueRequest = ReissueRequest.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        Token token = tokenCommandService.reissue(reissueRequest);

        ReissueTokenResponse response = ReissueTokenResponse.builder()
                .userId(token.getUserId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();

        ApiResponse<ReissueTokenResponse> tokenApiResponse = new ApiResponse<>(response,
                SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());

        return ResponseEntity.ok(tokenApiResponse);
    }


}

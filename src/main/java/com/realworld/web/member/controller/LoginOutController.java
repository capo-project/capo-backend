package com.realworld.web.member.controller;

import com.realworld.application.member.service.member.MemberService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.response.code.SuccessResponseCode;
import com.realworld.common.swagger.ExceptionResponseAnnotations;
import com.realworld.common.swagger.SuccessResponseAnnotation;
import com.realworld.common.swagger.SwaggerRequestBody;
import com.realworld.feature.auth.jwt.CustomUserDetails;
import com.realworld.web.member.payload.request.LoginRequest;
import com.realworld.web.member.payload.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "loginOut", description = "로그인, 로그아웃")
@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class LoginOutController {

    private final MemberService memberService;

    @Operation(
            summary = "로그인 요청 보내기",
            description = "로그인 API"
    )
    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @ExceptionResponseAnnotations({ExceptionResponseCode.NOT_EXISTS_USERID})
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<Object>> login(@SwaggerRequestBody(description = "로그인 유저 정보", required = true, content = @Content(schema = @Schema(implementation = LoginRequest.class))) @RequestBody final LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse(memberService.login(loginRequest));

        return ResponseEntity.ok(new SuccessResponse<>(loginResponse, SuccessResponseCode.SUCCESS.getResultCode(), SuccessResponseCode.SUCCESS.getHttpStatus(), "로그인 성공하였습니다."));
    }

    @Operation(
            summary = "로그아웃 요청 보내기",
            description = "로그아웃 API"
    )
    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @ExceptionResponseAnnotations({ExceptionResponseCode.NOT_EXISTS_USERID})
    @DeleteMapping("/logout")
    public ResponseEntity<SuccessResponse<Object>> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.logout(userDetails.getUserId());

        return ResponseEntity.ok(new SuccessResponse<>(null, SuccessResponseCode.SUCCESS.getResultCode(), SuccessResponseCode.SUCCESS.getHttpStatus(), "로그아웃 성공하였습니다."));
    }

}

package com.realworld.web.member.controller;

import com.realworld.application.member.service.member.MemberService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.common.response.code.SuccessCode;
import com.realworld.common.annotation.swagger.ExceptionResponseAnnotations;
import com.realworld.common.annotation.swagger.SuccessResponseAnnotation;
import com.realworld.common.annotation.swagger.SwaggerRequestBody;
import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.request.SignUpRequest;
import com.realworld.web.member.payload.response.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "signup", description = "회원가입")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class SignUpController {

    private final MemberService memberService;

    @Operation(
            summary = "회원 가입 요청 보내기",
            description = "회원 가입 API"
    )
    @SuccessResponseAnnotation(SuccessCode.CREATED)
    @ExceptionResponseAnnotations({ErrorCode.BAD_REQUEST_ERROR, ErrorCode.PASSWORD_MISS_MATCH_ERROR, ErrorCode.DUPLICATION_USERID_ERROR})
    @PostMapping(value = "/member")
    public ResponseEntity<SuccessResponse<SignUpResponse>> signUp(@SwaggerRequestBody(description = "회원 가입 요청 정보", required = true, content = @Content(schema = @Schema(implementation = SignUpRequest.class))) @Valid @RequestBody final SignUpRequest request) {
        final Member response = memberService.signUp(request);

        return ResponseEntity.created(URI.create("/api/v2/member/" + response.getUserId())).body(new SuccessResponse<>(null, SuccessCode.CREATED.getResultCode(), SuccessCode.CREATED.getHttpStatus(), "유저 생성 성공"));
    }

    @Operation(
            summary = "유저 아이디 중복 체크 요청",
            description = "유저 아이디 중복 체크 요청 API"
    )
    @SuccessResponseAnnotation(SuccessCode.SUCCESS)
    @GetMapping(value="/duplication-check/user-id/{user_id}")
    public ResponseEntity<SuccessResponse<Object>> validateDuplicate(@Parameter(description = "유저아이디", example="capotest1") @PathVariable("user_id") final String userId) {
        memberService.validateUserIdDuplicate(userId);

        return ResponseEntity.ok(new SuccessResponse<>(null, SuccessCode.SUCCESS.getResultCode(), SuccessCode.SUCCESS.getHttpStatus(), "회원가입 가능한 아이디입니다."));
    }

}

package com.realworld.web.member.controller;

import com.realworld.application.member.service.MemberService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.response.code.SuccessResponseCode;
import com.realworld.common.swagger.ExceptionResponseAnnotations;
import com.realworld.common.swagger.SuccessResponseAnnotation;
import com.realworld.common.swagger.SwaggerRequestBody;
import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.request.SignUpRequest;
import com.realworld.web.member.payload.response.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @SuccessResponseAnnotation(SuccessResponseCode.CREATED)
    @ExceptionResponseAnnotations({ExceptionResponseCode.BAD_REQUEST_ERROR, ExceptionResponseCode.PASSWORD_MISS_MATCH_ERROR, ExceptionResponseCode.DUPLICATION_USERID_ERROR})
    @PostMapping(value = "/member")
    public ResponseEntity<SuccessResponse<SignUpResponse>> signUp(@SwaggerRequestBody(description = "회원 가입 요청 정보", required = true, content = @Content(schema = @Schema(implementation = SignUpRequest.class))) @Valid @RequestBody final SignUpRequest request) {
        memberService.signUp(request);

        return ResponseEntity.ok(new SuccessResponse<>(null, SuccessResponseCode.CREATED.getResultCode(), SuccessResponseCode.CREATED.getHttpStatus(), "유저 생성 성공"));
    }

    @Operation(
            summary = "유저 아이디 중복 체크 요청",
            description = "유저 아이디 중복 체크 요청 API"
    )
    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @GetMapping
    public ResponseEntity<SuccessResponse<Object>> validateDuplicate(@RequestParam String userId) {
        return ResponseEntity.ok(new SuccessResponse<>(null, SuccessResponseCode.SUCCESS.getResultCode(), SuccessResponseCode.SUCCESS.getHttpStatus(), "회원가입 가능한 아이디입니다."));
    }

}

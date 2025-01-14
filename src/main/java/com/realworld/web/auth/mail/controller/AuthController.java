package com.realworld.web.auth.mail.controller;

import com.realworld.application.auth.mail.service.AuthMailService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.response.code.SuccessResponseCode;
import com.realworld.common.swagger.ExceptionResponseAnnotations;
import com.realworld.common.swagger.SuccessResponseAnnotation;
import com.realworld.common.swagger.SwaggerRequestBody;
import com.realworld.web.auth.mail.payload.request.AuthMailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name= "auth", description="인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/auth")
public class AuthController {

    private final AuthMailService authMailService;

    @Operation(
        summary = "이메일 인증 메일 보내기",
        description = "이메일 인증 메일 보내기 API"
    )
    @SuccessResponseAnnotation(SuccessResponseCode.CREATED)
    @ExceptionResponseAnnotations({ExceptionResponseCode.AUTH_EMAIL_REQUEST_ERROR, ExceptionResponseCode.AUTH_EMAIL_DUPLICATION_ERROR})
    @PostMapping(value="/email")
    public ResponseEntity<SuccessResponse<Object>> authMail(@Valid @SwaggerRequestBody(description = "이메일 인증 보내기 요청 정보", required = true, content = @Content(schema = @Schema(implementation = AuthMailRequest.class))) @RequestBody final AuthMailRequest request) {
        authMailService.send(request);

        return ResponseEntity.ok(new SuccessResponse<>(null, 201, HttpStatus.CREATED,"메일 전송 성공"));
    }

    @Operation(
            summary = "이메일 인증 번호 인증 성공 테스트",
            description = "이메일 인증 번호 인증 성공 API"
    )
    @SuccessResponseAnnotation(SuccessResponseCode.SUCCESS)
    @ExceptionResponseAnnotations({ExceptionResponseCode.AUTH_EMAIL_REQUEST_ERROR})
    @GetMapping(value="/email/{auth_number}")
    public ResponseEntity<SuccessResponse<String>> checkAuthMail(@Parameter(description = "유저 이메일", example = "test@naver.com") @RequestParam("user_email") final String userEmail, @Parameter(description = "인증번호", example = "asdf15130")@PathVariable("auth_number") final String authNumber) {
        authMailService.check(userEmail, authNumber);

        return ResponseEntity.ok(new SuccessResponse<>(null, 200, HttpStatus.OK ,"메일 전송 성공"));
    }

}

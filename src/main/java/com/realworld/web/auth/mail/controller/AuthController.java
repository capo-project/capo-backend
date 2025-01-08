package com.realworld.web.auth.mail.controller;

import com.realworld.application.auth.mail.service.AuthMailService;
import com.realworld.v1.global.code.SuccessCode;
import com.realworld.v1.global.response.ApiResponse;
import com.realworld.web.auth.mail.payload.request.AuthMailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/auth")
public class AuthController {

    private final AuthMailService authMailService;

    @PostMapping(value="/email")
    public ResponseEntity<Object> authMail(@RequestBody AuthMailRequest request) {
        authMailService.send(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(null,
                SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.INSERT_SUCCESS.getMessage()));
    }

    @GetMapping(value="/email/{auth_number}")
    public ResponseEntity<ApiResponse<String>> checkAuthMail(@RequestParam("user_email") String userEmail, @PathVariable("auth_number") String authNumber) {
        authMailService.check(userEmail, authNumber);

        return ResponseEntity.ok(new ApiResponse<>(null, 200, "이메일 인증 성공하였습니다."));
    }

}

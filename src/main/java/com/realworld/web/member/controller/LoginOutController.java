package com.realworld.web.member.controller;

<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginOutController {
=======

import com.realworld.common.response.SuccessResponse;
import com.realworld.v1.feature.loginout.LoginRequest;
import com.realworld.web.member.payload.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class LoginOutController {

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(final LoginRequest loginRequest){
        
        return ResponseEntity.ok(new SuccessResponse<>());
    }

>>>>>>> dev/v2-security
}

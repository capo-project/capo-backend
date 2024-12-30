package com.realworld.v1.feature.auth;

import com.realworld.v1.feature.auth.mail.AuthMailRequest;
import com.realworld.v1.feature.auth.mail.AuthMailService;
import com.realworld.v1.feature.member.service.MemberQueryService;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.code.SuccessCode;
import com.realworld.v1.global.config.exception.CustomMailExceptionHandler;
import com.realworld.v1.global.response.ApiResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthMailService authMailService;
    private final MemberQueryService getMemberUseCase;

    @PostMapping("/email")
    public ResponseEntity<ApiResponse<?>> emailAuth(@RequestBody AuthMailRequest request)
            throws MessagingException, UnsupportedEncodingException {

        authMailService.sendAuthNumber(request.getUserEmail());

        ApiResponse<?> authEmailApiResponse = new ApiResponse<>(null,
                SuccessCode.INSERT_SUCCESS.getStatus(), SuccessCode.INSERT_SUCCESS.getMessage());

        return ResponseEntity.status(HttpStatus.CREATED).body(authEmailApiResponse);
    }

    @GetMapping("/email/{auth_number}")
    public ResponseEntity<ApiResponse<?>> emailAuthNumber(@RequestParam("user_email") String userEmail,
                                                          @PathVariable("auth_number") String authNumber) {
        boolean isDuplicatedEmail = getMemberUseCase.existsByUserEmail(userEmail);

        if (isDuplicatedEmail) {
            throw new CustomMailExceptionHandler(ErrorCode.EMAIL_DUPLICATION_ERROR);
        }

        authMailService.checkEmailCode(userEmail, authNumber);

        ApiResponse<?> authEmailApiResponse = new ApiResponse<>(null,
                200, "이메일 인증에 성공하였습니다.");

        return ResponseEntity.ok(authEmailApiResponse);
    }

}

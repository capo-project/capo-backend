package com.realworld.feature.member.controller;

import com.realworld.feature.auth.mail.AuthMailService;
import com.realworld.feature.member.controller.request.FindPasswordRequest;
import com.realworld.feature.member.controller.request.UpdateEmailRequest;
import com.realworld.feature.member.controller.request.UpdatePasswordRequest;
import com.realworld.feature.member.controller.request.WithdrawMemberRequest;
import com.realworld.feature.member.controller.response.MemberResponse;
import com.realworld.feature.member.domain.Member;
import com.realworld.feature.member.service.MemberCommandService;
import com.realworld.feature.member.service.MemberQueryService;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.code.SuccessCode;
import com.realworld.global.config.exception.CustomMailExceptionHandler;
import com.realworld.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final AuthMailService authMailService;


    /**
     * 회원 정보 가져오기
     */
    @GetMapping("")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(@AuthenticationPrincipal User user) {
        Member member = memberQueryService.getMemberByUserId(user.getUsername());

        MemberResponse memberResponse = MemberResponse.builder()
                .userId(member.getUserId())
                .userEmail(member.getUserEmail())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .build();

        ApiResponse<MemberResponse> memberApiResponse = new ApiResponse<>(memberResponse,
                SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

        return ResponseEntity.ok(memberApiResponse);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("")
    public ResponseEntity<ApiResponse<?>> userRemove(@AuthenticationPrincipal User user, @RequestBody WithdrawMemberRequest request) {
        memberCommandService.remove(user.getUsername(), request.getPassword());

        ApiResponse<?> memberApiResponse = new ApiResponse<>(null,
                SuccessCode.DELETE_SUCCESS.getStatus(), SuccessCode.DELETE_SUCCESS.getMessage());

        /*
        A successful response of DELETE requests SHOULD be HTTP response code 200 (OK) if the response includes an entity describing the status,
        202 (Accepted) if the action has been queued,
        or 204 (No Content) if the action has been performed but the response does not include an entity. - RFC 7231
         */

        return ResponseEntity.ok(memberApiResponse);
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<?>> passwordUpdate(@AuthenticationPrincipal User user, @RequestBody UpdatePasswordRequest request) {

        Member targetMember = memberQueryService.getMemberByUserId(user.getUsername());

        Member member = Member.builder()
                .userId(user.getUsername())
                .userEmail(targetMember.getUserEmail())
                .currentPassword(request.getCurrentPassword())
                .newPassword(request.getNewPassword())
                .build();

        memberCommandService.updatePassword(member);

        ApiResponse<?> memberApiResponse = new ApiResponse<>(null,
                SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());

        return ResponseEntity.ok(memberApiResponse);
    }

    /**
     * 이메일 변경
     */
    @PatchMapping("/email")
    public ResponseEntity<ApiResponse<?>> emailUpdate(@AuthenticationPrincipal User user, @RequestBody UpdateEmailRequest request) {
        boolean exists = memberQueryService.existsByUserEmail(request.getUserEmail());
        if (exists) {
            throw new CustomMailExceptionHandler(ErrorCode.EMAIL_DUPLICATION_ERROR);
        }

        // 이메일 인증을 체크한다.
        authMailService.checkEmailCode(request.getUserEmail(), request.getAuthNumber());

        Member member = Member.builder()
                .userId(user.getUsername())
                .userEmail(request.getUserEmail())
                .build();

        long updated = memberCommandService.updateEmail(member);

        ApiResponse<?> memberApiResponse = new ApiResponse<>(null,
                SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());

        return ResponseEntity.ok(memberApiResponse);
    }

    /**
     * 비밀번호 찾기
     */
    @PatchMapping("/find-password")
    public ResponseEntity<ApiResponse<?>> findPassword(@RequestBody FindPasswordRequest request) {
        // 이메일 인증. 이메일로 비밀번호 찾음.
        authMailService.checkEmailCode(request.getUserEmail(), request.getAuthNumber());

        Member member = Member.builder()
                .userEmail(request.getUserEmail())
                .newPassword(request.getNewPassword())
                .build();

        memberCommandService.updatePassword(member);

        ApiResponse<?> memberApiResponse = new ApiResponse<>(null,
                SuccessCode.UPDATE_SUCCESS.getStatus(), SuccessCode.UPDATE_SUCCESS.getMessage());

        return ResponseEntity.ok(memberApiResponse);
    }

    /**
     * 아이디 찾기
     *
     * @param userEmail
     * @param authNumber
     * @return
     */
    @GetMapping("/find-userId/{auth_number}")
    public ResponseEntity<ApiResponse<String>> findUserId(@RequestParam("user_email") String userEmail,
                                                          @PathVariable("auth_number") String authNumber) {
        // 이메일 인증으로 아이디 찾기
        authMailService.checkEmailCode(userEmail, authNumber);

        Member member = memberQueryService.findByUserEmail(userEmail).orElseThrow();

        ApiResponse<String> memberApiResponse = new ApiResponse<>(member.getUserId(),
                SuccessCode.SELECT_SUCCESS.getStatus(), SuccessCode.SELECT_SUCCESS.getMessage());

        return ResponseEntity.ok(memberApiResponse);
    }
}

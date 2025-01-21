package com.realworld.web.member.controller;

import com.realworld.application.member.service.member.MemberService;
import com.realworld.common.annotation.swagger.ExceptionResponseAnnotations;
import com.realworld.common.annotation.swagger.SuccessResponseAnnotation;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.common.response.code.SuccessCode;
import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.response.MemberFindResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "member", description = "회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "회원 가입 요청 보내기",
            description = "회원 가입 API"
    )
    @SuccessResponseAnnotation(SuccessCode.SUCCESS)
    @ExceptionResponseAnnotations({ErrorCode.NOT_EXISTS_USERID})
    @GetMapping(value = "/{user_id}")
    public ResponseEntity<SuccessResponse<MemberFindResponse>> find(@Parameter(description = "유저아이디", example="capotest1") @PathVariable(value = "user_id") final String userId) {

        Member member = memberService.findById(userId);

        MemberFindResponse memberFindResponse = MemberFindResponse
                .builder()
                .memberProfile(member.getMemberProfile())
                .userId(member.getUserId())
                .build();

        return ResponseEntity.ok(new SuccessResponse<>(memberFindResponse, SuccessCode.SUCCESS.getResultCode(), SuccessCode.SUCCESS.getHttpStatus(), SuccessCode.SUCCESS.getMessage()));
    }

}

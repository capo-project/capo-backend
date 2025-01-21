package com.realworld.web.member.controller;

import com.realworld.application.member.service.member.MemberService;
import com.realworld.common.response.SuccessResponse;
import com.realworld.common.response.code.SuccessCode;
import com.realworld.feature.member.entity.Member;
import com.realworld.web.member.payload.response.MemberFindResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/{user_id}")
    public ResponseEntity<SuccessResponse<MemberFindResponse>> find(@PathVariable(value = "user_id") final String userId) {

        Member member = memberService.findById(userId);

        MemberFindResponse memberFindResponse = MemberFindResponse
                .builder()
                .memberProfile(member.getMemberProfile())
                .userId(member.getUserId())
                .build();

        return ResponseEntity.ok(new SuccessResponse<>(memberFindResponse, SuccessCode.SUCCESS.getResultCode(), SuccessCode.SUCCESS.getHttpStatus(), SuccessCode.SUCCESS.getMessage()));
    }

}

package com.realworld.project.adapter.in.web.member;

import com.realworld.project.application.port.in.PostMemberUseCase;
import com.realworld.project.common.utils.response.CommonUtil;
import com.realworld.project.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/photocard/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final PostMemberUseCase postMemberUseCase;

    @GetMapping("/test")
    public String test(){
        return "test";
    }
   @PostMapping("/member")
    public ResponseEntity memberRegister(@RequestBody Member member){
        if(CommonUtil.isEmpty(member)){

        }
        postMemberUseCase.saveMember(member);
        return (ResponseEntity) ResponseEntity.ok();
    }
}
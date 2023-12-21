package com.realworld.project.application.port.in.member;

import com.realworld.project.application.port.in.dto.MemberDTO;
import com.realworld.project.application.port.in.dto.TokenDTO;


public interface PostMemberUseCase {
    void saveMember(MemberDTO memberDto);
    TokenDTO login(MemberDTO memberDto);

}

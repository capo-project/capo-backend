package com.realworld.web.member.payload;

import lombok.Builder;

@Builder
public record SignUpRequest(String userId, String userEmail, String checkPassword, String password) {

}

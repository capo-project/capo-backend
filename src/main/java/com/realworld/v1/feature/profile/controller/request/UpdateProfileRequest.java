package com.realworld.feature.profile.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateProfileRequest {
    @NotNull(message = "nickname은 필수값입니다.")
    private String nickname;

    private String content;
}

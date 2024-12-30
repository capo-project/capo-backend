package com.realworld.feature.profile.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProfileResponse {
    private String nickname;
    private String content;
}

package com.realworld.v1.feature.member.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realworld.v1.feature.auth.Authority;
import com.realworld.v1.feature.file.domain.FileV1;
import com.realworld.v1.feature.member.entity.MemberJpaEntityV1;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private String userId;

    private String password;

    private String currentPassword;
    private String newPassword;

    private String content;
    private String phoneNumber;

    private String userEmail;

    private LocalDateTime regDt;

    private LocalDateTime createDt;

    private String delYn;

    private Authority authority;

    private String nickname;

    private FileV1 fileV1;

    private String oauthImage;

    public MemberJpaEntityV1 toEntity() {
        return MemberJpaEntityV1.builder()
                .userId(getUserId())
                .userEmail(getUserEmail())
                .password(getPassword())
                .phoneNumber(getPhoneNumber())
                .nickname(getNickname())
                .createDt(getCreateDt())
                .regDt(getRegDt())
                .delYn(getDelYn())
                .file(Objects.isNull(getFileV1()) ? null : getFileV1().toEntity())
                .oauthImage(getOauthImage())
                .authority(getAuthority())
                .build();
    }

    public MemberJpaEntityV1 productToEntity() {
        return MemberJpaEntityV1.builder()
                .userId(this.userId)
                .userEmail(this.userEmail)
                .nickname(this.nickname)
                .build();
    }
}

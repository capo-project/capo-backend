package com.realworld.feature.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberProfile {

    @Column(name = "user_email")
    private String userEmail;

    private String content;

    private String thumbnail;

    private String nickname;

    @Builder
    private MemberProfile(String userEmail, String content, String thumbnail, String nickname) {
        this.userEmail = userEmail;
        this.content = content;
        this.thumbnail = thumbnail;
        this.nickname = nickname;
    }

    public static MemberProfile createMemberProfile(String userEmail, String nickname) {
        return MemberProfile.builder()
                .userEmail(userEmail)
                .nickname(nickname)
                .build();
    }

}

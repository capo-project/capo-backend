package com.realworld.feature.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberProfile {
    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    private String content;

    private String thumbnail;

    private String nickname;

    @Builder
    private MemberProfile(final String userEmail, final String content, final String thumbnail, final String nickname) {
        this.userEmail = userEmail;
        this.content = content;
        this.thumbnail = thumbnail;
        this.nickname = nickname;
    }

    public static MemberProfile createMemberProfile(final String userEmail, final String nickname) {
        return MemberProfile.builder()
                .userEmail(userEmail)
                .nickname(nickname)
                .build();
    }

}

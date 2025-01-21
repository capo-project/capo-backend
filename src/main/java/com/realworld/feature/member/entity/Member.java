package com.realworld.feature.member.entity;

import com.realworld.common.exception.custom.CustomMemberExceptionHandler;
import com.realworld.common.holder.date.DateTimeHolder;
import com.realworld.common.holder.nickname.NicknameGeneratorHolder;
import com.realworld.common.holder.password.PasswordEncodeHolder;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.v1.feature.auth.Authority;
import com.realworld.web.member.payload.request.SignUpRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
public class Member {

    @Id
    @NotNull
    @Column(name = "user_id")
    private String userId;

    @NotNull
    private String password;

    @Embedded
    private MemberProfile memberProfile;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @NotNull
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @NotNull
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Builder
    private Member(final String userId, final String password, final MemberProfile memberProfile, final Authority authority, final LocalDateTime registerDate, LocalDateTime modifyDate) {
        this.userId = userId;
        this.password = password;
        this.memberProfile = memberProfile;
        this.authority = authority;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
    }

    private Member(final String userId, final String password, final String checkPassword, final MemberProfile memberProfile, final Authority authority, final LocalDateTime registerDate, final LocalDateTime modifyDate) {
        this.userId = userId;
        this.password = password;
        this.memberProfile = memberProfile;
        this.authority = authority;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
        this.isValidatePassword(checkPassword);
    }

    public static Member createMember(final SignUpRequest request, DateTimeHolder dateTimeHolder, NicknameGeneratorHolder nicknameGeneratorHolder) {
        return new Member(request.userId(), request.password(), request.checkPassword(), MemberProfile.createMemberProfile(request.userEmail(), nicknameGeneratorHolder.generate()), Authority.ROLE_USER, dateTimeHolder.generate(), dateTimeHolder.generate());
    }

    public Member passwordEncode(PasswordEncodeHolder encoder) {
        return Member.builder()
                .userId(this.userId)
                .password(encoder.encode(this.password))
                .memberProfile(this.memberProfile)
                .authority(this.authority)
                .registerDate(this.registerDate)
                .modifyDate(this.modifyDate)
                .build();
    }

    public void isValidatePassword(final String checkPassword) {
        if (!this.password.equals(checkPassword)) {
            throw new CustomMemberExceptionHandler(ErrorCode.PASSWORD_MISS_MATCH_ERROR);
        }
    }

}

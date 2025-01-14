package com.realworld.feature.member.entity;

import com.realworld.common.holder.date.DateTimeHolder;
import com.realworld.common.holder.nickname.NicknameGeneratorHolder;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.exception.CustomMemberExceptionHandler;
import com.realworld.v1.feature.auth.Authority;
import com.realworld.web.member.payload.request.SignUpRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

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
        this.isValidatePassword(password, checkPassword);

    }

    public static Member createMember(final SignUpRequest request, DateTimeHolder dateTimeHolder, NicknameGeneratorHolder nicknameGeneratorHolder) {
        return new Member(request.userId(), request.password(), request.checkPassword(), MemberProfile.createMemberProfile(request.userEmail(), nicknameGeneratorHolder.generate()), Authority.ROLE_USER, dateTimeHolder.generate(), dateTimeHolder.generate());
    }

    private void isValidatePassword(final String password, final String checkPassword) {
        if (!password.equals(checkPassword)) {
            throw new CustomMemberExceptionHandler(ExceptionResponseCode.PASSWORD_MISS_MATCH_ERROR);
        }
    }

}

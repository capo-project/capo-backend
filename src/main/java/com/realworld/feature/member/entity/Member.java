package com.realworld.feature.member.entity;

import com.realworld.common.holder.date.DateTimeHolder;
import com.realworld.common.holder.nickname.NicknameGeneratorHolder;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.exception.CustomMemberExceptionHandler;
import com.realworld.v1.feature.auth.Authority;
import com.realworld.web.member.payload.SignUpRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
public class Member {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String password;

    @Embedded
    private MemberProfile memberProfile;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Builder
    private Member(String userId, String password, MemberProfile memberProfile, Authority authority, LocalDateTime registerDate, LocalDateTime modifyDate) {
        this.userId = userId;
        this.password = password;
        this.memberProfile = memberProfile;
        this.authority = authority;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
    }

    private Member(String userId, String password, String checkPassword, MemberProfile memberProfile, Authority authority, LocalDateTime registerDate, LocalDateTime modifyDate) {
        this.userId = userId;
        this.password = password;
        this.memberProfile = memberProfile;
        this.authority = authority;
        this.registerDate = registerDate;
        this.modifyDate = modifyDate;
        isValidatePassword(password, checkPassword);
    }

    public static Member createMember(SignUpRequest request, DateTimeHolder dateTimeHolder, NicknameGeneratorHolder nicknameGeneratorHolder) {
        return new Member(request.userId(), request.password(), request.checkPassword(), MemberProfile.createMemberProfile(request.userEmail(), nicknameGeneratorHolder.generate()), Authority.ROLE_USER, dateTimeHolder.generate(), dateTimeHolder.generate());
    }

    private void isValidatePassword(String password, String checkPassword) {
        if (!password.equals(checkPassword)) {
            throw new CustomMemberExceptionHandler(ExceptionResponseCode.PASSWORD_MISS_MATCH_ERROR);
        }

    }

}

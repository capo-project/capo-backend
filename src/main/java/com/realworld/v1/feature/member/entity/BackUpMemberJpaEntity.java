package com.realworld.feature.member.entity;

import com.realworld.feature.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.realworld.feature.auth.Authority;
import java.time.LocalDateTime;

@Table(name="backup_user")
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BackUpMemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private String userId;

    private String password;

    private String phoneNumber;

    private String userEmail;

    private String delYn;

    private String nickname;

    @LastModifiedDate
    private LocalDateTime regDt;

    @CreatedDate
    private LocalDateTime createDt;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public BackUpMemberJpaEntity(Long userSeq, String userId, String password,String phoneNumber, String userEmail, String delYn, LocalDateTime regDt, LocalDateTime createDt, Authority authority, String nickname){
        this.userSeq = userSeq;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.delYn = delYn;
        this.regDt = regDt;
        this.createDt = createDt;
        this.authority = authority;
        this.password = password;
        this.nickname = nickname;
    }

    public BackUpMemberJpaEntity memberConvertBackupEntity(Member member){

        return BackUpMemberJpaEntity.builder() // BackUpMemberJpaEntity Builder 패턴
                .authority(Authority.ROLE_USER)
                .userId(member.getUserId())
                .phoneNumber(member.getPhoneNumber())
                .userEmail(member.getUserEmail())
                .delYn("N")
                .password(member.getPassword())
                .nickname(member.getNickname())
                .createDt(member.getCreateDt())
                .regDt(member.getRegDt())
                .build();
    }
}


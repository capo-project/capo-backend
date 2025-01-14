package com.realworld.v1.feature.member.entity;

import com.realworld.v1.feature.auth.Authority;
import com.realworld.v1.feature.file.entity.FileJpaEntity;
import com.realworld.v1.feature.like.entity.ProductLikeJpaEntity;
import com.realworld.v1.feature.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@Table(name = "userV1", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
@Slf4j
public class MemberJpaEntityV1 {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String password;

    private String phoneNumber;

    private String userEmail;

    private String delYn;

    private String nickname;

    private String content;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileJpaEntity file;

    @LastModifiedDate
    private LocalDateTime regDt;

    @CreatedDate
    private LocalDateTime createDt;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "oauth_image")
    private String oauthImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductLikeJpaEntity> likes;

    public Member toDomain() {
        return Member.builder()
                .password(getPassword())
                .userId(getUserId())
                .userEmail(getUserEmail())
                .phoneNumber(getPhoneNumber())
                .fileV1(Objects.isNull(getFile()) ? null : getFile().toDomain())
                .nickname(getNickname())
                .content(getContent())
                .createDt(getCreateDt())
                .regDt(getRegDt())
                .delYn(getDelYn())
                .authority(getAuthority())
                .build();
    }

    public Member productToDomain() {
        return Member.builder()
                .userId(this.userId)
                .userEmail(this.userEmail)
                .nickname(this.nickname)
                .build();
    }
}

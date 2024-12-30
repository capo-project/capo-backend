package com.realworld.feature.auth.mail.entity;

import com.realworld.feature.auth.mail.domain.AuthMail;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "auth_mail")
@Entity
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class AuthMailJpaEntity {
    @Id
    private String userEmail;

    private String authNumber;

    @LastModifiedDate
    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

    @Builder
    public AuthMailJpaEntity(String userEmail, String authNumber, LocalDateTime regDt) {
        this.userEmail = userEmail;
        this.authNumber = authNumber;
        this.regDt = regDt;
    }

    public AuthMail toDomain() {
        return AuthMail.builder()
                .authNumber(getAuthNumber())
                .userEmail(getUserEmail())
                .registerDate(getRegDt())
                .build();
    }
}

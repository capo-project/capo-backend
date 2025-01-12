package com.realworld.feature.auth.mail.entity;

import com.realworld.common.holder.auth.key.MailKeyGeneratorHolder;
import com.realworld.common.holder.date.DateTimeHolder;
import com.realworld.infrastructure.mail.MailSender;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomAuthMailExceptionHandler;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Slf4j
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value="authMail", timeToLive = 360)
public class AuthMail {
    @Id
    private String userEmail;

    private String authNumber;

    private LocalDateTime registerDate;

    @Builder
    private AuthMail(final String userEmail, final String authNumber, final LocalDateTime registerDate) {
        this.userEmail = userEmail;
        this.authNumber = authNumber;
        this.registerDate = registerDate;
    }

    public static AuthMail createMail(final String userEmail, MailKeyGeneratorHolder keyGeneratorHolder, DateTimeHolder dateTimeHolder) {

        return new AuthMail(userEmail, keyGeneratorHolder.generate(), dateTimeHolder.generate());
    }

    public String send(MailSender mailSender) {
        final String message = createMessage();
        mailSender.send(message, this.userEmail);
        return message;
    }

    private String createMessage() {
        String msg = "<div style='margin:100px;'>";
        msg += "<h1> 인증번호 : " + this.authNumber + "</h1>";
        msg += "</div>";
        return msg;
    }

    public void authCheck(String authNumber) {
        if(!this.authNumber.equals(authNumber)) {
            throw new CustomAuthMailExceptionHandler(ErrorCode.AUTH_EMAIL_AUTH_NUMBER_ERROR);
        }
    }

}

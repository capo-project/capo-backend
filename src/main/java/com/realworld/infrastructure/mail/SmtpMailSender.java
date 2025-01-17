package com.realworld.infrastructure.mail;

import com.realworld.common.exception.custom.CustomAuthMailExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmtpMailSender implements MailSender {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(String message, String userEmail) {
        javaMailSender.send(createMessage(message, userEmail));
    }

    private MimeMessage createMessage(final String message, final String userEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            mimeMessage.setSubject("포토카드 이메일 인증 코드");
            mimeMessage.addRecipients(Message.RecipientType.TO, userEmail);
            mimeMessage.setText(message, "utf-8", "html");
            mimeMessage.setFrom(new InternetAddress(from, "PhotoCard_Admin"));
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("이메일 전송 실패 :: {}", e);
            throw new CustomAuthMailExceptionHandler(ErrorCode.AUTH_EMAIL_REQUEST_ERROR);
        }
        return mimeMessage;
    }

}

//package com.realworld.v1.infra.mail;
//
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
//
//import java.io.UnsupportedEncodingException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class SmtpMailSender implements MailSender {
//
//    private final JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    @Override
//    public void send(String userEmail, String code) throws MessagingException, UnsupportedEncodingException {
//        MimeMessage mimeMessage = createMessage(userEmail, code);
//
//        try {
//            javaMailSender.send(mimeMessage);
//        } catch (MailException es) {
//            log.error("이메일 전송 실패", es);
//            throw new IllegalArgumentException();
//        }
//    }
//
//    private MimeMessage createMessage(String email, String authKey) throws UnsupportedEncodingException, MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        // 제목
//        mimeMessage.setSubject("포토카드 이메일 인증 코드");
//
//        // 받는사람
//        mimeMessage.addRecipients(Message.RecipientType.TO, email);
//
//        // 내용
//        String msg = "<div style='margin:100px;'>";
//        msg += "<h1> 인증번호 : " + authKey + "</h1>";
//        msg += "</div>";
//        mimeMessage.setText(msg, "utf-8", "html");
//
//        // 보내는 사람
//        mimeMessage.setFrom(new InternetAddress(from, "PhotoCard_Admin"));
//
//        return mimeMessage;
//    }
//
//}

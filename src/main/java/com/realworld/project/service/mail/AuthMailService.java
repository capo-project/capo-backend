package com.realworld.project.service.mail;

import com.realworld.project.adapter.out.persistence.mail.AuthMailJpaEntity;
import com.realworld.project.adapter.out.persistence.member.MemberJpaEntity;
import com.realworld.project.application.port.in.mail.GetMailUseCase;
import com.realworld.project.application.port.out.mail.CommandAuthMailPort;
import com.realworld.project.application.port.out.member.LoadMemberPort;
import com.realworld.project.common.code.ErrorCode;
import com.realworld.project.common.config.exception.CustomMailExceptionHandler;
import com.realworld.project.domain.AuthMail;
import com.realworld.project.domain.Member;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthMailService implements GetMailUseCase {
    private final JavaMailSender javaMailSender;
    private final LoadMemberPort loadMemberPort;
    private final CommandAuthMailPort commandAuthMailPort;
    @Value("${spring.mail.username}")
    private String from;
    @Override
    public AuthMail emailAuth(String userEmail) throws MessagingException, UnsupportedEncodingException {
        String authKey = createKey();

        MimeMessage mimeMessage = createMessage(userEmail, authKey);
        try{
            javaMailSender.send(mimeMessage);
        } catch(MailException es) { // 우선 구현 ==> 메일도 커스텀하여 사용
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        AuthMail authMail = AuthMail.builder()
                            .userEmail(userEmail)
                            .authNumber(authKey)
                            .build();
        Optional<AuthMailJpaEntity> target= commandAuthMailPort.saveEmailAuth(authMail);

        if(target.isPresent()){
            return authMail;
        }  else {
            throw new CustomMailExceptionHandler(ErrorCode.EMAIL_REQUEST_ERROR);
        }

    }

    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i=0; i< 8; i++){
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch(index){
                case 0:
                    key.append((char)((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97 = 98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char)((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }

        return key.toString();
    }

    public MimeMessage createMessage(String email, String authKey) throws UnsupportedEncodingException, MessagingException {
        // email 메시지 보내는 대상
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        mimeMessage.addRecipients(Message.RecipientType.TO, email);
        mimeMessage.setSubject("photocard 회원가입 이메일 인증");

        String msg = "";
        msg += "<div style='margin:100px;'>";
        msg += "<h1> 인증번호 : "+authKey+"</h1>";

        mimeMessage.setText(msg, "utf-8", "html");
        mimeMessage.setFrom(new InternetAddress(from,"PhotoCard_Admin"));

        return mimeMessage;
    }
}
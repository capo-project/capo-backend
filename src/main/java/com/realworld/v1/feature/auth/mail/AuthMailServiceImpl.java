package com.realworld.feature.auth.mail;

import com.realworld.feature.auth.mail.domain.AuthMail;
import com.realworld.feature.auth.mail.entity.AuthMailJpaEntity;
import com.realworld.global.code.ErrorCode;
import com.realworld.global.config.exception.CustomMailExceptionHandler;
import com.realworld.infra.mail.MailSender;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import static com.realworld.global.code.ErrorCode.EMAIL_REQUEST_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthMailServiceImpl implements AuthMailService {

    private final AuthMailRepository authMailRepository;
    private final MailSender smtpMailSender;

    @Override
    public void sendAuthNumber(String userEmail) throws MessagingException, UnsupportedEncodingException {
        String authKey = createKey();

        smtpMailSender.send(userEmail, authKey);

        AuthMail authMail = AuthMail.builder()
                .userEmail(userEmail)
                .authNumber(authKey)
                .build();

        authMailRepository.save(authMail.toEntity());
    }

    @Override
    public void checkEmailCode(String userEmail, String authNumber) {
        AuthMailJpaEntity target = authMailRepository.findByUserEmail(userEmail).orElseThrow(() ->
                new CustomMailExceptionHandler(EMAIL_REQUEST_ERROR));

        AuthMail authMail = target.toDomain();

        boolean isEqualAuthNumber = authMail.isEqualAuthNumber(authNumber);
        if (!isEqualAuthNumber) throw new CustomMailExceptionHandler(ErrorCode.BAD_REQUEST_ERROR);

        boolean isExpiredAuthMail = authMail.isExpiredAuthMail(target.getRegDt());
        if (isExpiredAuthMail) throw new CustomMailExceptionHandler(ErrorCode.EMAIL_EXPIRED_ERROR);
    }

    private String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) (rnd.nextInt(26) + 97));
                    // a~z (ex. 1+97 = 98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) (rnd.nextInt(26) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }

        return key.toString().toLowerCase();
    }
}

package com.realworld.v1.feature.auth.mail;

import com.realworld.v1.feature.auth.mail.domain.AuthMail;
import com.realworld.v1.feature.auth.mail.entity.AuthMailJpaEntity;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomAuthMailExceptionHandlerV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.realworld.v1.global.code.ErrorCode.AUTH_EMAIL_REQUEST_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthMailServiceV1Impl implements AuthMailServiceV1 {

    private final AuthMailRepositoryV1 authMailRepositoryV1;
//    private final MailSender smtpMailSender;

    @Override
    public void sendAuthNumber(String userEmail) {
        String authKey = createKey();

//        smtpMailSender.send(userEmail, authKey);

        AuthMail authMail = AuthMail.builder()
                .userEmail(userEmail)
                .authNumber(authKey)
                .build();

        authMailRepositoryV1.save(authMail.toEntity());
    }

    @Override
    public void checkEmailCode(String userEmail, String authNumber) {
        AuthMailJpaEntity target = authMailRepositoryV1.findByUserEmail(userEmail).orElseThrow(() ->
                new CustomAuthMailExceptionHandlerV1(AUTH_EMAIL_REQUEST_ERROR));

        AuthMail authMail = target.toDomain();

        boolean isEqualAuthNumber = authMail.isEqualAuthNumber(authNumber);
        if (!isEqualAuthNumber) throw new CustomAuthMailExceptionHandlerV1(ErrorCode.BAD_REQUEST_ERROR);

        boolean isExpiredAuthMail = authMail.isExpiredAuthMail(target.getRegDt());
        if (isExpiredAuthMail) throw new CustomAuthMailExceptionHandlerV1(ErrorCode.AUTH_EMAIL_EXPIRED_ERROR);
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

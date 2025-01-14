package com.realworld.application.auth.mail.service;

import com.realworld.application.auth.mail.port.AuthMailRepository;
import com.realworld.common.exception.CustomMemberExceptionHandler;
import com.realworld.common.holder.auth.key.MailKeyGeneratorHolderImpl;
import com.realworld.common.holder.date.DateTimeHolderImpl;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.feature.auth.mail.entity.AuthMail;
import com.realworld.infrastructure.mail.MailSender;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomMemberExceptionHandlerV1;
import com.realworld.web.auth.mail.payload.request.AuthMailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthMailServiceImpl implements AuthMailService {

    private final AuthMailRepository authMailRepository;
    private final MailSender mailSender;

    @Override
    public void send(final AuthMailRequest request) {
        final AuthMail authMail = AuthMail.createMail(request.userEmail(), new MailKeyGeneratorHolderImpl(), new DateTimeHolderImpl());
        authMail.send(mailSender);

        authMailRepository.save(authMail);
    }

    @Override
    public void check(final String userEmail, final String authNumber) {
        final AuthMail authMail = authMailRepository.findByUserEmail(userEmail).orElseThrow(() -> new CustomMemberExceptionHandler(ExceptionResponseCode.AUTH_EMAIL_REQUEST_ERROR));

        authMail.authCheck(authNumber);
    }

}

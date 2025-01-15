package com.realworld.common.holder.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PasswordEncodeHolderImpl implements PasswordEncodeHolder{

    private final PasswordEncoder encoder;

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }

}

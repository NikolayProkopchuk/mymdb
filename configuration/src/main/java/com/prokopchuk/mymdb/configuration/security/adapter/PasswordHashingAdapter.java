package com.prokopchuk.mymdb.configuration.security.adapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.prokopchuk.mymdb.user.application.port.out.PasswordHashingPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordHashingAdapter implements PasswordHashingPort {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}

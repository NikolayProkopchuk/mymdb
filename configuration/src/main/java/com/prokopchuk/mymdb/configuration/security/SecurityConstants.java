package com.prokopchuk.mymdb.configuration.security;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    public static final long EXPIRATION_TIME = 846000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SIGN_UP_URL = "/users";
    public static final SecretKey TOKEN_SECRET = Jwts.SIG.HS256.key().build();
}

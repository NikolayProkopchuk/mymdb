package com.prokopchuk.mymdb.configuration.security;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokopchuk.mymdb.configuration.security.model.SecurityUserDetails;
import com.prokopchuk.mymdb.user.adapter.web.dto.req.LoginUserRequestDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
        throws AuthenticationException {
        try {
            LoginUserRequestDto loginDto =
              new ObjectMapper().readValue(req.getInputStream(), LoginUserRequestDto.class);

            return authenticationManager
              .authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password(), new ArrayList<>()));
        } catch (IOException ex) {
            throw new AuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain,
                                            Authentication authResult) {
        var user = (SecurityUserDetails) authResult.getPrincipal();
        log.info("user: {} successfully authenticated", user.getUsername());
        Key key = Keys.hmacShaKeyFor(SecurityConstants.TOKEN_SECRET.getEncoded());
        String token = Jwts.builder()
          .subject(user.getUsername())
          .expiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
          .signWith(key)
          .compact();

        resp.addHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + token);
    }
}

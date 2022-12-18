package com.prokopchuk.mymdb.config;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokopchuk.mymdb.adapter.in.web.dto.req.LoginUserRequestDto;
import com.prokopchuk.mymdb.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

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
        var user = (User) authResult.getPrincipal();
        Key key = Keys.hmacShaKeyFor(SecurityConstants.TOKEN_SECRET.getEncoded());
        String token = Jwts.builder()
          .setSubject(user.getUsername())
          .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
          .signWith(key)
          .compact();

        resp.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        resp.addHeader("UserId", user.getPublicId());
    }
}

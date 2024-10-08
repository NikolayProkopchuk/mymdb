package com.prokopchuk.mymdb.configuration.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        String token = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, resp);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req, resp);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

        String username = Jwts.parser()
          .verifyWith(SecurityConstants.TOKEN_SECRET)
          .build()
          .parseSignedClaims(token)
          .getPayload()
          .getSubject();

        if (username != null) {
            var user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }
        return null;
    }
}

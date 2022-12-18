package com.prokopchuk.mymdb.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        String token = req.getHeader(SecurityConstants.HEADER_STRING);

        if (token == null || !token.startsWith("Bearer")) {
            chain.doFilter(req, resp);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req, resp);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

        String user = Jwts.parserBuilder()
          .setSigningKey(SecurityConstants.TOKEN_SECRET)
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }
        return null;
    }
}

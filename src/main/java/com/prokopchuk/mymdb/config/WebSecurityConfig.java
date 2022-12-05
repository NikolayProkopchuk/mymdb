package com.prokopchuk.mymdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;


@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, BCryptPasswordEncoder passwordEncoder)
        throws Exception {

        httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder);

        return httpSecurity
          .csrf()
          .disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.GET, "/films")
          .permitAll()
          .antMatchers(HttpMethod.POST, "/users")
          .permitAll()
          .anyRequest()
          .authenticated()
          .and().build();
    }

}

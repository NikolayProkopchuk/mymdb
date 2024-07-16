package com.prokopchuk.mymdb.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, BCryptPasswordEncoder passwordEncoder)
            throws Exception {
        var authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        var authenticationManager = authenticationManagerBuilder.build();

        return httpSecurity
                .authenticationManager(authenticationManager)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auths -> auths.requestMatchers(HttpMethod.GET, "/films/*").permitAll()
                                .requestMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                                .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .addFilter(authenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager, userDetailsService))
                .sessionManagement(sessionManagement -> sessionManagement
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    private AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager) {
        var authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/users/login");

        return authenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

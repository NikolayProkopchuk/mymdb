package com.prokopchuk.mymdb.config;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.prokopchuk.mymdb.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.domain.Role;
import com.prokopchuk.mymdb.domain.User;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = User
          .builder()
          .username("testUser")
          .password("testUserPass")
          .enabled(true)
          .accountNonExpired(true)
          .accountNonLocked(true)
          .credentialsNonExpired(true)
          .build();

        basicUser.addRole(Role.ROLE_USER);

        LoadUserPort loadUserPort = Mockito.mock(LoadUserPort.class);

        when(loadUserPort.loadUserByUsername("testUser")).thenReturn(Optional.of(basicUser));


        return new UserDetailsServiceImpl(loadUserPort);
    }
}

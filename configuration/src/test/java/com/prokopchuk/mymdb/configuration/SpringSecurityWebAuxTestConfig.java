package com.prokopchuk.mymdb.configuration;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.configuration.security.UserDetailsServiceImpl;
import com.prokopchuk.mymdb.configuration.security.mapper.UserToSecurityUserDetailsMapper;
import com.prokopchuk.mymdb.configuration.security.model.SecurityRole;
import com.prokopchuk.mymdb.configuration.security.model.SecurityUserDetails;
import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.user.domain.Role;
import com.prokopchuk.mymdb.user.domain.User;


@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = User
          .builder()
          .id(new UserId(1L))
          .username("testUser")
          .password("testUserPass")
          .enabled(true)
          .accountNonExpired(true)
          .accountNonLocked(true)
          .credentialsNonExpired(true)
          .build();

        basicUser.addRole(Role.ROLE_USER);

        SecurityUserDetails userDetails = SecurityUserDetails.builder()
          .id(1L)
          .username("testUser")
          .password("testUserPass")
          .enabled(true)
          .accountNonExpired(true)
          .accountNonLocked(true)
          .credentialsNonExpired(true)
          .authorities(Set.of(SecurityRole.ROLE_USER))
          .build();

        LoadUserPort loadUserPort = Mockito.mock(LoadUserPort.class);
        UserToSecurityUserDetailsMapper userToSecurityUserDetailsMapper =
          Mockito.mock(UserToSecurityUserDetailsMapper.class);

        when(loadUserPort.loadUserByUsername("testUser")).thenReturn(Optional.of(basicUser));
        when(userToSecurityUserDetailsMapper.userToUserDetailsDto(basicUser)).thenReturn(userDetails);

        return new UserDetailsServiceImpl(loadUserPort, userToSecurityUserDetailsMapper);
    }
}

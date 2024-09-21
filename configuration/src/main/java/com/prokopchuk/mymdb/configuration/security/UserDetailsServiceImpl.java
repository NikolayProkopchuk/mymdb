package com.prokopchuk.mymdb.configuration.security;

import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prokopchuk.mymdb.configuration.security.model.SecurityUserDetails;
import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoadUserPort loadUserPort;
    private final ConversionService conversionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserPort.loadUserByUsername(username)
          .map(user -> conversionService.convert(user, SecurityUserDetails.class))
          .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found by username : %s", username)));
    }
}

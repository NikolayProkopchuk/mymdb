package com.prokopchuk.mymdb.configuration.security;

import com.prokopchuk.mymdb.configuration.security.mapper.UserToSecurityUserDetailsMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoadUserPort loadUserPort;
    private final UserToSecurityUserDetailsMapper userToSecurityUserDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserPort.loadUserByUsername(username)
                .map(userToSecurityUserDetailsMapper::userToUserDetailsDto)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found by username : %s", username)));
    }
}

package com.prokopchuk.mymdb.application.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prokopchuk.mymdb.application.UserException;
import com.prokopchuk.mymdb.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.application.service.mapper.UserCommandUserMapper;
import com.prokopchuk.mymdb.domain.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
class UserRegisterService implements UserRegisterUseCase {

    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;

    private final UserCommandUserMapper userCommandUserMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerUser(RegisterUserCommand userCommand) {
        if (loadUserPort.loadUserByUsername(userCommand.username()).isPresent()) {
            throw new UserException(String.format("User with username: '%s' exists", userCommand.username()));
        }
        if (loadUserPort.loadUserByEmail(userCommand.email()).isPresent()) {
            throw new UserException(String.format("User with email: '%s' exists", userCommand.email()));
        }

        var user = userCommandUserMapper.userCommandToUser(userCommand);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(Role.ROLE_USER);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPublicId(RandomStringUtils.randomAlphabetic(10));
        var createdUser = registerUserPort.registerUser(user);

        return createdUser.getPublicId();
    }
}

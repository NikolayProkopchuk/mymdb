package com.prokopchuk.mymdb.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prokopchuk.mymdb.application.UserException;
import com.prokopchuk.mymdb.application.port.in.CreateUserCommand;
import com.prokopchuk.mymdb.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.domain.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserRegisterService implements UserRegisterUseCase {

    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;

    private final UserCommandUserMapper userCommandUserMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long registerUser(CreateUserCommand userCommand) {
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
        var createdUser = registerUserPort.registerUser(user);

        return createdUser.getId();
    }
}

package com.prokopchuk.mymdb.user.application.service;

import com.prokopchuk.mymdb.common.application.annotation.UseCase;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.application.exception.UserException;
import com.prokopchuk.mymdb.user.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.user.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.user.application.service.mapper.UserCommandUserMapper;
import com.prokopchuk.mymdb.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UseCase
@RequiredArgsConstructor
class UserRegisterService implements UserRegisterUseCase {

    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserCommandUserMapper userCommandUserMapper;

    @Override
    public UserId registerUser(RegisterUserCommand userCommand) {
        if (loadUserPort.loadUserByUsername(userCommand.username()).isPresent()) {
            throw new UserException(String.format("User with username: '%s' exists", userCommand.username()));
        }
        if (loadUserPort.loadUserByEmail(userCommand.email()).isPresent()) {
            throw new UserException(String.format("User with email: '%s' exists", userCommand.email()));
        }

        var user = userCommandUserMapper.userCommandToUser(userCommand);
        user.setPassword(passwordEncoder.encode(userCommand.password()));
        user.addRole(Role.ROLE_USER);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        var createdUser = registerUserPort.registerUser(user);

        return createdUser.getId();
    }
}

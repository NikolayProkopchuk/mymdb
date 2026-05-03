package com.prokopchuk.mymdb.user.application.service;

import java.util.Objects;

import org.springframework.core.convert.ConversionService;

import com.prokopchuk.mymdb.common.application.annotation.UseCase;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.application.exception.UserNotUniqueException;
import com.prokopchuk.mymdb.user.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.user.application.port.out.PasswordHashingPort;
import com.prokopchuk.mymdb.user.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.user.domain.User;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
class UserRegisterService implements UserRegisterUseCase {

    private final LoadUserPort loadUserPort;
    private final RegisterUserPort registerUserPort;
    private final PasswordHashingPort passwordHashingPort;

    private final ConversionService conversionService;

    @Override
    public UserId registerUser(RegisterUserCommand userCommand) {
        if (loadUserPort.loadUserByUsername(userCommand.getUsername()).isPresent()) {
            throw new UserNotUniqueException(
              String.format("User with username: '%s' exists", userCommand.getUsername()));
        }
        if (loadUserPort.loadUserByEmail(userCommand.getEmail()).isPresent()) {
            throw new UserNotUniqueException(String.format("User with email: '%s' exists", userCommand.getUsername()));
        }

        var user = conversionService.convert(userCommand, User.class);
        Objects.requireNonNull(user);
        String hashed = passwordHashingPort.hash(userCommand.getPassword());
        user.initializeOnRegistration(hashed);
        var createdUser = registerUserPort.registerUser(user);

        return createdUser.getId();
    }
}

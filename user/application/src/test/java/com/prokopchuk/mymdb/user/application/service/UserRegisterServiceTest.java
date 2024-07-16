package com.prokopchuk.mymdb.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.application.exception.UserException;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.user.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.user.application.service.mapper.UserCommandUserMapper;
import com.prokopchuk.mymdb.user.domain.Role;
import com.prokopchuk.mymdb.user.domain.Sex;
import com.prokopchuk.mymdb.user.domain.User;

class UserRegisterServiceTest {

    private final LoadUserPort loadUserPort = Mockito.mock(LoadUserPort.class);

    private final RegisterUserPort registerUserPort = Mockito.mock(RegisterUserPort.class);

    private final UserCommandUserMapper userCommandUserMapper = Mappers.getMapper(UserCommandUserMapper.class);

    private final BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);


    private final UserRegisterService registerUserService = new UserRegisterService(
            loadUserPort,
            registerUserPort,
            passwordEncoder,
            userCommandUserMapper);

    @Test
    void shouldThrowUserExceptionIfUserWithUsernameExists() {
        var createUserCommand = createUserCommand();
        var user = createUserToSave();

        given(loadUserPort.loadUserByUsername("test")).willReturn(Optional.of(user));
        Assertions.assertThrows(UserException.class,
          () -> registerUserService.registerUser(createUserCommand));
    }

    @Test
    void shouldThrowUserExceptionIfUserWithEmailExists() {
        var createUserCommand = createUserCommand();
        var user = createUserToSave();

        given(loadUserPort.loadUserByEmail("test@mail.com")).willReturn(Optional.of(user));
        Assertions.assertThrows(UserException.class,
          () -> registerUserService.registerUser(createUserCommand));
    }

    @Test
    void shouldCreateUserAndReturnHisId() {
        var createUserCommand = createUserCommand();
        var userToSave = createUserToSave();
        var savedUser = createSavedUser();

        given(loadUserPort.loadUserByEmail("test@mail.com")).willReturn(Optional.empty());
        given(loadUserPort.loadUserByUsername("test")).willReturn(Optional.empty());
        given(registerUserPort.registerUser(userToSave)).willReturn(savedUser);

        assertThat(registerUserService.registerUser(createUserCommand)).isEqualTo(new UserId(1L));
        //todo: add check that user has role ROLE_USER
    }

    private RegisterUserCommand createUserCommand() {
        return new RegisterUserCommand(
          "test",
          "test@mail.com",
          "test",
          Sex.MALE,
          "test",
          "test",
          LocalDate.of(1988, Month.APRIL, 10));
    }

    private User createUserToSave() {
        return User.builder()
          .username("test")
          .email("test@mail.com")
          .password("test")
          .firstName("test")
          .lastName("test")
          .birthday(LocalDate.of(1988, Month.APRIL, 10))
          .sex(Sex.MALE)
                .roles(Set.of(Role.ROLE_USER))
          .build();
    }

    private User createSavedUser() {

        return User.builder()
                .id(new UserId(1L))
                .username("test")
                .email("test@mail.com")
                .password("test")
                .firstName("test")
                .lastName("test")
                .birthday(LocalDate.of(1988, Month.APRIL, 10))
                .sex(Sex.MALE)
                .roles(Set.of(Role.ROLE_USER))
                .build();
    }
}

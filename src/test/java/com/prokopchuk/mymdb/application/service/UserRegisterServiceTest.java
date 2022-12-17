package com.prokopchuk.mymdb.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import com.prokopchuk.mymdb.application.UserException;
import com.prokopchuk.mymdb.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.domain.Role;
import com.prokopchuk.mymdb.domain.Sex;
import com.prokopchuk.mymdb.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserRegisterServiceTest {

    private final LoadUserPort loadUserPort = Mockito.mock(LoadUserPort.class);

    private final RegisterUserPort registerUserPort = Mockito.mock(RegisterUserPort.class);

    private final UserCommandUserMapper userCommandUserMapper = Mappers.getMapper(UserCommandUserMapper.class);

    private final BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    private final UserRegisterService registerUserService = new UserRegisterService(loadUserPort,
      registerUserPort,
      userCommandUserMapper,
      passwordEncoder);

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

        assertThat(registerUserService.registerUser(createUserCommand)).isEqualTo("abcdefghij");
        //todo: add check that user has role ROLE_USER
        then(passwordEncoder).should().encode(eq("test"));
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
          .build();
    }

    private User createSavedUser() {
        var user = User.builder()
          .id(1L)
          .username("test")
          .email("test@mail.com")
          .publicId("abcdefghij")
          .password("test")
          .firstName("test")
          .lastName("test")
          .birthday(LocalDate.of(1988, Month.APRIL, 10))
          .sex(Sex.MALE)
          .build();
        user.addRole(Role.ROLE_USER);

        return user;
    }
}

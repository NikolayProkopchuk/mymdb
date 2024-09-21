package com.prokopchuk.mymdb.user.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.application.exception.UserNotUniqueException;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.user.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.user.domain.Role;
import com.prokopchuk.mymdb.user.domain.Sex;
import com.prokopchuk.mymdb.user.domain.User;

class UserRegisterServiceTest {

    private final LoadUserPort loadUserPort = Mockito.mock(LoadUserPort.class);

    private final RegisterUserPort registerUserPort = Mockito.mock(RegisterUserPort.class);

    private final ConversionService conversionService = Mockito.mock(ConversionService.class);

    private final BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);


    private final UserRegisterService registerUserService = new UserRegisterService(
            loadUserPort,
            registerUserPort,
            passwordEncoder,
            conversionService);

    @Test
    void shouldThrowUserExceptionIfUserWithUsernameExists() {
        var createUserCommand = createUserCommand();
        var user = createUserToSave();

        given(loadUserPort.loadUserByUsername("test")).willReturn(Optional.of(user));
        Assertions.assertThrows(UserNotUniqueException.class,
          () -> registerUserService.registerUser(createUserCommand));
    }

    @Test
    void shouldThrowUserExceptionIfUserWithEmailExists() {
        var createUserCommand = createUserCommand();
        var user = createUserToSave();

        given(loadUserPort.loadUserByEmail("test@mail.com")).willReturn(Optional.of(user));
        Assertions.assertThrows(UserNotUniqueException.class,
          () -> registerUserService.registerUser(createUserCommand));
    }

    @Test
    void shouldCreateUserAndReturnHisId() {
        var createUserCommand = createUserCommand();
        var userToSave = createUserToSave();
        var savedUser = createSavedUser();

        given(loadUserPort.loadUserByEmail("test@mail.com")).willReturn(Optional.empty());
        given(loadUserPort.loadUserByUsername("test")).willReturn(Optional.empty());
        given(conversionService.convert(createUserCommand, User.class)).willReturn(userToSave);
        given(registerUserPort.registerUser(userToSave)).willReturn(savedUser);

        assertThat(registerUserService.registerUser(createUserCommand)).isEqualTo(new UserId(1L));
        assertThat(userToSave.getRoles()).hasSize(1);
        assertThat(userToSave.getRoles()).contains(Role.ROLE_USER);
        assertThat(userToSave.isAccountNonExpired()).isTrue();
        assertThat(userToSave.isAccountNonLocked()).isTrue();
        assertThat(userToSave.isCredentialsNonExpired()).isTrue();
        assertThat(userToSave.isEnabled()).isTrue();
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

        return User.builder()
                .id(new UserId(1L))
                .username("test")
                .email("test@mail.com")
                .password("test")
                .firstName("test")
                .lastName("test")
                .birthday(LocalDate.of(1988, Month.APRIL, 10))
                .sex(Sex.MALE)
                .roles(new HashSet<>(Set.of(Role.ROLE_USER)))
                .build();
    }
}

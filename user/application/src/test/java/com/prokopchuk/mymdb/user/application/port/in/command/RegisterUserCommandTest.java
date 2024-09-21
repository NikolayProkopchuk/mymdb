package com.prokopchuk.mymdb.user.application.port.in.command;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.prokopchuk.mymdb.user.domain.Sex;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

class RegisterUserCommandTest {

    @Test
    void validationOk() {
        new RegisterUserCommand(
          "test",
          "test@mail.com",
          "test",
          Sex.MALE,
          "test",
          "test",
          LocalDate.now()
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validationFailWhenUsernameNotValid(String username) {
        Set<ConstraintViolation<?>> constraintViolations = assertThrows(ConstraintViolationException.class,
          () -> new RegisterUserCommand(
            username,
            "test@mail.com",
            "test",
            Sex.MALE,
            "test",
            "test",
            LocalDate.now()
          )).getConstraintViolations();
        assertThat(constraintViolations).hasSize(1);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"notValidEmail"})
    void validationFailWhenEmailNotValid(String email) {
        Set<ConstraintViolation<?>> constraintViolations = assertThrows(ConstraintViolationException.class,
          () -> new RegisterUserCommand(
            "test",
            email,
            "test",
            Sex.MALE,
            "test",
            "test",
            LocalDate.now()
          )).getConstraintViolations();
        assertThat(constraintViolations).hasSize(1);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validationFailWhenPasswordNotValid(String password) {
        Set<ConstraintViolation<?>> constraintViolations = assertThrows(ConstraintViolationException.class,
          () -> new RegisterUserCommand(
            "test",
            "test@mail.com",
            password,
            Sex.MALE,
            "test",
            "test",
            LocalDate.now()
          )).getConstraintViolations();
        assertThat(constraintViolations).hasSize(1);
    }

    @ParameterizedTest
    @NullSource
    void validationFailWhenSexNotValid(Sex sex) {
        Set<ConstraintViolation<?>> constraintViolations = assertThrows(ConstraintViolationException.class,
          () -> new RegisterUserCommand(
            "test",
            "test@mail.com",
            "password",
            sex,
            "test",
            "test",
            LocalDate.now()
          )).getConstraintViolations();
        assertThat(constraintViolations).hasSize(1);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validationFailWhenFirstNameNotValid(String firstName) {
        Set<ConstraintViolation<?>> constraintViolations = assertThrows(ConstraintViolationException.class,
          () -> new RegisterUserCommand(
            "test",
            "test@mail.com",
            "password",
            Sex.MALE,
            firstName,
            "test",
            LocalDate.now()
          )).getConstraintViolations();
        assertThat(constraintViolations).hasSize(1);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validationFailWhenLastNameNotValid(String lastName) {
        Set<ConstraintViolation<?>> constraintViolations = assertThrows(ConstraintViolationException.class,
          () -> new RegisterUserCommand(
            "test",
            "test@mail.com",
            "password",
            Sex.MALE,
            "test",
            lastName,
            LocalDate.now()
          )).getConstraintViolations();
        assertThat(constraintViolations).hasSize(1);
    }

    @ParameterizedTest
    @NullSource
    void validationFailWhenBirthdayNotValid(LocalDate birthday) {
        Set<ConstraintViolation<?>> constraintViolations = assertThrows(ConstraintViolationException.class,
          () -> new RegisterUserCommand(
            "test",
            "test@mail.com",
            "password",
            Sex.MALE,
            "test",
            "test",
            birthday
          )).getConstraintViolations();
        assertThat(constraintViolations).hasSize(1);
    }
}
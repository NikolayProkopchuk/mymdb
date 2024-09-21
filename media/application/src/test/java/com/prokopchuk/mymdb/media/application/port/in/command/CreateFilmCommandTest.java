package com.prokopchuk.mymdb.media.application.port.in.command;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import jakarta.validation.ConstraintViolationException;

class CreateFilmCommandTest {

    @Test
    void validationOk() {
        new CreateFilmCommand(
          "test",
          "test description",
          LocalDate.now()
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validationFailOnName(String name) {
        assertThrows(ConstraintViolationException.class,
          () -> new CreateFilmCommand(
          name,
          "test description",
          LocalDate.now()
        ));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validationFailOnDescription(String description) {
        assertThrows(ConstraintViolationException.class,
          () -> new CreateFilmCommand(
            "test",
            description,
            LocalDate.now()
          ));
    }

    @ParameterizedTest
    @NullSource
    void validationFailOnCreationDate(LocalDate creationDate) {
        assertThrows(ConstraintViolationException.class,
          () -> new CreateFilmCommand(
            "test",
            "test description",
            creationDate
          ));
    }

}
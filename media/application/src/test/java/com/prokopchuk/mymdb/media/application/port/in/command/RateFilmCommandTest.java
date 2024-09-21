package com.prokopchuk.mymdb.media.application.port.in.command;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.media.domain.Rating;

import jakarta.validation.ConstraintViolationException;

class RateFilmCommandTest {

    @Test
    void validationOk() {
        new RateFilmCommand(
          new FilmId(1L),
          new UserId(1L),
          Rating.ONE
        );
    }

    @Test
    void validationFailedOnFilmId() {
        assertThrows(ConstraintViolationException.class,
          () -> new RateFilmCommand(
            null,
            new UserId(1L),
            Rating.ONE));
    }

    @Test
    void validationFailedOnUserId() {
        assertThrows(ConstraintViolationException.class,
          () -> new RateFilmCommand(
            new FilmId(1L),
            null,
            Rating.ONE));
    }

    @Test
    void validationFailedOnRating() {
        assertThrows(ConstraintViolationException.class,
          () -> new RateFilmCommand(
            new FilmId(1L),
            new UserId(1L),
            null));
    }

}

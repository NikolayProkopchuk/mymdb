package com.prokopchuk.mymdb.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.prokopchuk.mymdb.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.application.port.out.LoadFilmPort;
import com.prokopchuk.mymdb.application.port.out.RateFilmPort;
import com.prokopchuk.mymdb.domain.Film;
import com.prokopchuk.mymdb.domain.Rating;
import com.prokopchuk.mymdb.domain.User;
import com.prokopchuk.mymdb.domain.UserRating;

class RateFilmServiceTest {
    private final RateFilmPort rateFilmPort = Mockito.mock(RateFilmPort.class);

    private final LoadFilmPort loadFilmPort = Mockito.mock(LoadFilmPort.class);


    private final RateFilmService rateFilmService = new RateFilmService(
      rateFilmPort,
      loadFilmPort
    );

    @Test
    void shouldCreateUserFilmRating() {
        long filmId = 1L;

        var user = User.builder()
          .username("testUserName")
          .build();

        var rateFilmCommand = new RateFilmCommand(
          filmId,
          user,
          1
        );

        var film = Film.builder()
          .id(filmId)
          .build();

        var userFilmRating = new UserRating(
          user,
          film,
          Rating.getInstance(rateFilmCommand.getRating())
        );

        given(loadFilmPort.loadFilmById(rateFilmCommand.getFilmId())).willReturn(Optional.of(film));
        given(rateFilmPort.rate(userFilmRating)).willReturn(userFilmRating);

        assertThat(rateFilmService.rate(rateFilmCommand)).isEqualTo(userFilmRating);
        then(loadFilmPort).should().loadFilmById(rateFilmCommand.getFilmId());
        then(rateFilmPort).should().rate(userFilmRating);
    }

    @Test
    void shouldThrowExceptionWhenFilmIsNotExist() {
        long filmId = 1;
        var user = User.builder()
          .username("testUserName")
          .build();
        given(loadFilmPort.loadFilmById(filmId)).willReturn(Optional.empty());

        var rateFilmCommand = new RateFilmCommand(
          filmId,
          user,
          1
        );

        assertThrows(NoSuchElementException.class, () -> rateFilmService.rate(rateFilmCommand));
    }
}

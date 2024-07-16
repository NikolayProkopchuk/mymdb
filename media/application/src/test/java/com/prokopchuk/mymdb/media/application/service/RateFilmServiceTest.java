package com.prokopchuk.mymdb.media.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.media.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.media.application.port.out.LoadFilmPort;
import com.prokopchuk.mymdb.media.application.port.out.RateFilmPort;
import com.prokopchuk.mymdb.media.domain.Film;
import com.prokopchuk.mymdb.media.domain.Rating;
import com.prokopchuk.mymdb.media.domain.UserRating;

class RateFilmServiceTest {
    private final RateFilmPort rateFilmPort = Mockito.mock(RateFilmPort.class);

    private final LoadFilmPort loadFilmPort = Mockito.mock(LoadFilmPort.class);


    private final RateFilmService rateFilmService = new RateFilmService(
      rateFilmPort,
      loadFilmPort
    );

    @Test
    void shouldCreateUserFilmRating() {
        FilmId filmId = new FilmId(1L);
        UserId userId = new UserId(1L);

        var rateFilmCommand = new RateFilmCommand(
                filmId,
                userId,
                1
        );

        var userFilmRating = new UserRating(
          userId,
          filmId,
          Rating.getInstance(rateFilmCommand.getRating())
        );

        Film film = Film.builder()
                .id(filmId)
                .build();

        given(loadFilmPort.loadFilmById(filmId.getValue())).willReturn(Optional.of(film));
        given(rateFilmPort.rate(userFilmRating)).willReturn(userFilmRating);

        assertThat(rateFilmService.rate(rateFilmCommand)).isEqualTo(userFilmRating);
        then(loadFilmPort).should().loadFilmById(filmId.getValue());
        then(rateFilmPort).should().rate(userFilmRating);
    }

    @Test
    void shouldThrowExceptionWhenFilmIsNotExist() {
        FilmId filmId = new FilmId(1L);
        UserId userId = new UserId(1L);
        given(loadFilmPort.loadFilmById(filmId.getValue())).willReturn(Optional.empty());

        var rateFilmCommand = new RateFilmCommand(
          filmId,
          userId,
          1
        );

        assertThrows(NoSuchElementException.class, () -> rateFilmService.rate(rateFilmCommand));
    }
}

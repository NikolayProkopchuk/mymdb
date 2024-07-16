package com.prokopchuk.mymdb.media.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;
import com.prokopchuk.mymdb.media.application.port.out.CreateFilmPort;
import com.prokopchuk.mymdb.media.application.service.mapper.CreateFilmCommandToFilmMapper;
import com.prokopchuk.mymdb.media.domain.Film;

class CreateFilmServiceTest {

    private final CreateFilmPort createFilmPort = Mockito.mock(CreateFilmPort.class);

    private final CreateFilmCommandToFilmMapper createFilmCommandToFilmMapper = Mockito.mock(
      CreateFilmCommandToFilmMapper.class);

    private final CreateFilmService createFilmService =
      new CreateFilmService(createFilmPort, createFilmCommandToFilmMapper);

    @Test
    void shouldCreateFilmAndReturnItsId() {
        String name = "testFilm";
        String description = "test description";
        LocalDate productionDate = LocalDate.of(2000, 1, 1);
        long createdFilmId = 1;
        var createFilmCommand = new CreateFilmCommand(
          name,
          description,
          productionDate
        );

        var film = Film.builder()
          .name(name)
          .description(description)
          .productionDate(productionDate)
          .build();

        var createdFilm = Film.builder()
          .id(new FilmId(createdFilmId))
          .name(name)
          .description(description)
          .productionDate(productionDate)
          .build();

        given(createFilmCommandToFilmMapper.createFilmCommandToFilm(createFilmCommand)).willReturn(film);
        given(createFilmPort.createFilm(film)).willReturn(createdFilm);

        assertThat(createFilmService.createFilm(createFilmCommand)).isEqualTo(createdFilmId);

        then(createFilmCommandToFilmMapper).should().createFilmCommandToFilm(createFilmCommand);
        then(createFilmPort).should().createFilm(film);
    }
}

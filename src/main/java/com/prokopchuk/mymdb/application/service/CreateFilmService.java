package com.prokopchuk.mymdb.application.service;

import com.prokopchuk.mymdb.application.port.in.CreateFilmUseCase;
import com.prokopchuk.mymdb.application.port.in.command.CreateFilmCommand;
import com.prokopchuk.mymdb.application.port.out.CreateFilmPort;
import com.prokopchuk.mymdb.application.service.mapper.CreateFilmCommandToFilmMapper;
import com.prokopchuk.mymdb.common.annotation.UseCase;
import com.prokopchuk.mymdb.domain.Film;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateFilmService implements CreateFilmUseCase {

    private final CreateFilmPort createFilmPort;

    private final CreateFilmCommandToFilmMapper createFilmCommandToFilmMapper;

    @Override
    public Long createFilm(CreateFilmCommand createFilmCommand) {
        Film film = createFilmCommandToFilmMapper.createFilmCommandToFilm(createFilmCommand);
        Film createdFilm = createFilmPort.createFilm(film);

        return createdFilm.getId();
    }
}

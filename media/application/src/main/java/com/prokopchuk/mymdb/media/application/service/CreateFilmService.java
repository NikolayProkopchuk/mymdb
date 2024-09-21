package com.prokopchuk.mymdb.media.application.service;

import org.springframework.core.convert.ConversionService;

import com.prokopchuk.mymdb.common.application.annotation.UseCase;
import com.prokopchuk.mymdb.media.application.port.in.CreateFilmUseCase;
import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;
import com.prokopchuk.mymdb.media.application.port.out.CreateFilmPort;
import com.prokopchuk.mymdb.media.domain.Film;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateFilmService implements CreateFilmUseCase {

    private final CreateFilmPort createFilmPort;

    private final ConversionService conversionService;

    @Override
    public Long createFilm(CreateFilmCommand createFilmCommand) {
        Film film = conversionService.convert(createFilmCommand, Film.class);
        Film createdFilm = createFilmPort.createFilm(film);

        return createdFilm.getId().getValue();
    }
}

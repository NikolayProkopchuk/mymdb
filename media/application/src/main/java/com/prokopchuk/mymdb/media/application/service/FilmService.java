package com.prokopchuk.mymdb.media.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prokopchuk.mymdb.common.application.annotation.UseCase;
import com.prokopchuk.mymdb.media.application.port.in.FilmDtoQuery;
import com.prokopchuk.mymdb.media.application.port.in.FilmEntityDto;
import com.prokopchuk.mymdb.media.application.port.out.LoadFilmPort;

import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
class FilmService implements FilmDtoQuery {

    private final LoadFilmPort loadFilmPort;

    @Override
    public Page<FilmEntityDto> getFilms(Pageable pageable) {
        return loadFilmPort.loadFilmEntityDtos(pageable);
    }

    @Override
    public FilmEntityDto getFilm(Long id) {
        return loadFilmPort.loadFilmEntityDtoById(id).orElseThrow();
    }
}

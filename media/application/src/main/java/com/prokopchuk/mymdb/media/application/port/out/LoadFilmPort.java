package com.prokopchuk.mymdb.media.application.port.out;

import java.util.Optional;

import com.prokopchuk.mymdb.media.application.port.in.FilmEntityDto;
import com.prokopchuk.mymdb.media.domain.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoadFilmPort {

    Optional<Film> loadFilmById(Long id);

    Optional<FilmEntityDto> loadFilmEntityDtoById(Long id);

    Page<FilmEntityDto> loadFilmEntityDtos(Pageable pageable);
}

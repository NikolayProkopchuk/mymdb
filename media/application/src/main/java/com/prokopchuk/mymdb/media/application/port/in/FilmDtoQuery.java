package com.prokopchuk.mymdb.media.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmDtoQuery {

    Page<FilmEntityDto> getFilms(Pageable pageable);

    FilmEntityDto getFilm(Long id);

}

package com.prokopchuk.mymdb.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntityDto;

public interface FilmDtoQuery {

    Page<FilmEntityDto> getFilms(Pageable pageable);

    FilmEntityDto getFilm(Long id);

}

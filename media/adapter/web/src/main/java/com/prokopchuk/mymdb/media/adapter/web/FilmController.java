package com.prokopchuk.mymdb.media.adapter.web;

import com.prokopchuk.mymdb.media.application.port.in.FilmEntityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prokopchuk.mymdb.media.application.port.in.FilmDtoQuery;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
class FilmController {

    private final FilmDtoQuery filmDtoQuery;

    @GetMapping
    Page<FilmEntityDto> getFilms(Pageable pageable) {
        return filmDtoQuery.getFilms(pageable);
    }

    @GetMapping("/{id}")
    FilmEntityDto getFilm(@PathVariable("id") Long id) {
        return filmDtoQuery.getFilm(id);
    }

}

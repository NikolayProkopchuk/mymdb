package com.prokopchuk.mymdb.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntityDto;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.FilmRepo;
import com.prokopchuk.mymdb.application.port.in.FilmDtoQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FilmService implements FilmDtoQuery {

    private final FilmRepo filmRepo;

    @Override
    public Page<FilmEntityDto> getFilms(Pageable pageable) {
        return filmRepo.findFilmsDto(pageable);
    }

    @Override
    public FilmEntityDto getFilm(Long id) {
        return filmRepo.findFilmDto(id);
    }
}

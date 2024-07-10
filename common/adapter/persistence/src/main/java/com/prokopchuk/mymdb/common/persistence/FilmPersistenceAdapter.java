package com.prokopchuk.mymdb.common.persistence;

import java.util.Optional;

import com.prokopchuk.mymdb.common.persistence.annotation.PersistenceAdapter;
import com.prokopchuk.mymdb.common.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.common.persistence.mapper.FilmFilmEntityMapper;
import com.prokopchuk.mymdb.common.persistence.repo.FilmRepo;
import com.prokopchuk.mymdb.media.application.port.in.FilmEntityDto;
import com.prokopchuk.mymdb.media.application.port.out.CreateFilmPort;
import com.prokopchuk.mymdb.media.application.port.out.LoadFilmPort;
import com.prokopchuk.mymdb.media.domain.Film;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@PersistenceAdapter
@RequiredArgsConstructor
public class FilmPersistenceAdapter implements CreateFilmPort, LoadFilmPort {

    private final FilmRepo filmRepo;

    private final FilmFilmEntityMapper filmFilmEntityMapper;

    @Override
    public Film createFilm(Film film) {
        FilmEntity filmEntity = filmFilmEntityMapper.filmToFilmEntity(film);
        FilmEntity savedFilmEntity = filmRepo.save(filmEntity);
        return filmFilmEntityMapper.filmEntityToFilm(savedFilmEntity);
    }

    @Override
    public Optional<Film> loadFilmById(Long id) {
        return filmRepo.findById(id).map(filmFilmEntityMapper::filmEntityToFilm);
    }

    @Override
    public Optional<FilmEntityDto> loadFilmEntityDtoById(Long id) {
        return filmRepo.findFilmDto(id);
    }

    @Override
    public Page<FilmEntityDto> loadFilmEntityDtos(Pageable pageable) {
        return filmRepo.findFilmsDto(pageable);
    }
}

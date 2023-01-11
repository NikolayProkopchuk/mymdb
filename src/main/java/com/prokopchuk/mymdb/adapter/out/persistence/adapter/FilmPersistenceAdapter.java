package com.prokopchuk.mymdb.adapter.out.persistence.adapter;

import java.util.Optional;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.mapper.FilmFilmEntityMapper;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.FilmRepo;
import com.prokopchuk.mymdb.application.port.out.CreateFilmPort;
import com.prokopchuk.mymdb.application.port.out.LoadFilmPort;
import com.prokopchuk.mymdb.common.annotation.PersistenceAdapter;
import com.prokopchuk.mymdb.domain.Film;

import lombok.RequiredArgsConstructor;

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
}

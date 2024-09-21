package com.prokopchuk.mymdb.common.persistence;

import java.util.Objects;
import java.util.Optional;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prokopchuk.mymdb.common.persistence.annotation.PersistenceAdapter;
import com.prokopchuk.mymdb.common.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.common.persistence.repo.FilmRepo;
import com.prokopchuk.mymdb.media.application.port.in.FilmEntityDto;
import com.prokopchuk.mymdb.media.application.port.out.CreateFilmPort;
import com.prokopchuk.mymdb.media.application.port.out.LoadFilmPort;
import com.prokopchuk.mymdb.media.domain.Film;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class FilmPersistenceAdapter implements CreateFilmPort, LoadFilmPort {

    private final FilmRepo filmRepo;

    private final ConversionService conversionService;

    @Override
    public Film createFilm(Film film) {
        FilmEntity filmEntity = conversionService.convert(film, FilmEntity.class);
        Objects.requireNonNull(filmEntity);
        FilmEntity savedFilmEntity = filmRepo.save(filmEntity);
        return conversionService.convert(savedFilmEntity, Film.class);
    }

    @Override
    public Optional<Film> loadFilmById(Long id) {
        return filmRepo.findById(id)
          .map(filmEntity -> conversionService.convert(filmEntity, Film.class));
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

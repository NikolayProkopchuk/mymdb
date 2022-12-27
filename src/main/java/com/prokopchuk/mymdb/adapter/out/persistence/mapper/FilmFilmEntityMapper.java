package com.prokopchuk.mymdb.adapter.out.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.domain.Film;

@Mapper
public interface FilmFilmEntityMapper {

    FilmEntity filmToFilmEntity(Film film);

    @Mapping(target = "mymdbRating", ignore = true)
    Film filmEntityToFilm(FilmEntity filmEntity);
}

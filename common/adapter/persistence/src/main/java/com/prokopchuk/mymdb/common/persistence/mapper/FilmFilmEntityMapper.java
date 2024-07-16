package com.prokopchuk.mymdb.common.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.common.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.media.domain.Film;

@Mapper(uses = BaseIdToLongMapper.class)
public interface FilmFilmEntityMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    FilmEntity filmToFilmEntity(Film film);

    Film filmEntityToFilm(FilmEntity filmEntity);
}

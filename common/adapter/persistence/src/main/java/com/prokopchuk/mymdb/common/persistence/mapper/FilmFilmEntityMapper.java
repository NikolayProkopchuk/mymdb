package com.prokopchuk.mymdb.common.persistence.mapper;

import com.prokopchuk.mymdb.common.persistence.entity.FilmEntity;
import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.media.domain.Film;
import org.mapstruct.Mapping;

@Mapper(uses = BaseIdToLongMapper.class)
public interface FilmFilmEntityMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    FilmEntity filmToFilmEntity(Film film);

    Film filmEntityToFilm(FilmEntity filmEntity);
}

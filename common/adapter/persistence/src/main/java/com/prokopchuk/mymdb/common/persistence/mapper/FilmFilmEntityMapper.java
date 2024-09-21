package com.prokopchuk.mymdb.common.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.prokopchuk.mymdb.common.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.media.domain.Film;

@Mapper(uses = BaseIdToLongMapper.class)
public interface FilmFilmEntityMapper extends Converter<Film, FilmEntity> {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    FilmEntity convert(@NonNull Film film);

    @InheritInverseConfiguration
    @DelegatingConverter
    Film invertConvert(FilmEntity filmEntity);
}

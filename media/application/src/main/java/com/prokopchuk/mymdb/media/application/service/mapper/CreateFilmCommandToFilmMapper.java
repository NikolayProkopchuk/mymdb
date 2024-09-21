package com.prokopchuk.mymdb.media.application.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;
import com.prokopchuk.mymdb.media.domain.Film;

@Mapper
public interface CreateFilmCommandToFilmMapper extends Converter<CreateFilmCommand, Film> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Film convert(@NonNull CreateFilmCommand createFilmCommand);
}

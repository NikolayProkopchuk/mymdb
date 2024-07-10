package com.prokopchuk.mymdb.media.application.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;
import com.prokopchuk.mymdb.media.domain.Film;

@Mapper
public interface CreateFilmCommandToFilmMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Film createFilmCommandToFilm(CreateFilmCommand createFilmCommand);
}

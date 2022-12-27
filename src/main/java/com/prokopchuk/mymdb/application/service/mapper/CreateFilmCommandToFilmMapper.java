package com.prokopchuk.mymdb.application.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.application.port.in.command.CreateFilmCommand;
import com.prokopchuk.mymdb.domain.Film;

@Mapper
public interface CreateFilmCommandToFilmMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mymdbRating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Film createFilmCommandToFilm(CreateFilmCommand createFilmCommand);
}

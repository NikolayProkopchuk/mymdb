package com.prokopchuk.mymdb.media.adapter.web.mapper;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.prokopchuk.mymdb.media.adapter.web.dto.req.CreateFilmRequestDto;
import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;

@Mapper
public interface CreateFilmRequestToCommandMapper extends Converter<CreateFilmRequestDto, CreateFilmCommand> {

    CreateFilmCommand convert(@NonNull CreateFilmRequestDto createFilmRequestDto);
}

package com.prokopchuk.mymdb.media.adapter.web.mapper;

import com.prokopchuk.mymdb.media.adapter.web.dto.req.CreateFilmRequestDto;
import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;

@Mapper
public interface CreateFilmRequestToCommandMapper {

    CreateFilmCommand requestToCommand(CreateFilmRequestDto createFilmRequestDto);
}

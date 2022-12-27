package com.prokopchuk.mymdb.adapter.in.web.mapper;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.adapter.in.web.dto.req.CreateFilmRequestDto;
import com.prokopchuk.mymdb.application.port.in.command.CreateFilmCommand;

@Mapper
public interface CreateFilmRequestToCommandMapper {

    CreateFilmCommand requestToCommand(CreateFilmRequestDto createFilmRequestDto);
}

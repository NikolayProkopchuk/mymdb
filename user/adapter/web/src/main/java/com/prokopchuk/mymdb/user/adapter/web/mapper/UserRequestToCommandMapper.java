package com.prokopchuk.mymdb.user.adapter.web.mapper;


import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import com.prokopchuk.mymdb.user.adapter.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;

import jakarta.validation.constraints.NotNull;

@Mapper
public interface UserRequestToCommandMapper extends Converter<RegisterUserRequestDto, RegisterUserCommand> {

    RegisterUserCommand convert(@NotNull RegisterUserRequestDto dto);
}

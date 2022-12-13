package com.prokopchuk.mymdb.adapter.in.web.mapper;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.adapter.in.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.application.port.in.RegisterUserCommand;

@Mapper
public interface UserRequestToCommandMapper {

    RegisterUserCommand registerUserRequestToCommand(RegisterUserRequestDto dto);
}

package com.prokopchuk.mymdb.adapter.in.web.mapper;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.adapter.in.web.dto.req.CreateUserRequestDto;
import com.prokopchuk.mymdb.application.port.in.CreateUserCommand;

@Mapper
public interface UserRequestToCommandMapper {

    CreateUserCommand createUserRequestToCommand(CreateUserRequestDto dto);
}

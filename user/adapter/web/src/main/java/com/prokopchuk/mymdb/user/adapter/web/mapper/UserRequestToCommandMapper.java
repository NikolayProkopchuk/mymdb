package com.prokopchuk.mymdb.user.adapter.web.mapper;


import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.user.adapter.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;

@Mapper
public interface UserRequestToCommandMapper {

    RegisterUserCommand registerUserRequestToCommand(RegisterUserRequestDto dto);
}

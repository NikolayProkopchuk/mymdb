package com.prokopchuk.mymdb.user.adapter.web.mapper;


import com.prokopchuk.mymdb.user.adapter.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import org.mapstruct.Mapper;

@Mapper
public interface UserRequestToCommandMapper {

    RegisterUserCommand registerUserRequestToCommand(RegisterUserRequestDto dto);
}

package com.prokopchuk.mymdb.user.application.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.domain.User;

@Mapper
public interface UserCommandUserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User userCommandToUser(RegisterUserCommand registerUserCommand);
}

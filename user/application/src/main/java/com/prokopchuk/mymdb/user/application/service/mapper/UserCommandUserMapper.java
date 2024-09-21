package com.prokopchuk.mymdb.user.application.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.domain.User;

@Mapper
public interface UserCommandUserMapper extends Converter<RegisterUserCommand, User> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User convert(@NonNull RegisterUserCommand registerUserCommand);
}

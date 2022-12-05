package com.prokopchuk.mymdb.application.service;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.application.port.in.CreateUserCommand;
import com.prokopchuk.mymdb.domain.User;

@Mapper
public interface UserCommandUserMapper {

    User userCommandToUser(CreateUserCommand createUserCommand);
}

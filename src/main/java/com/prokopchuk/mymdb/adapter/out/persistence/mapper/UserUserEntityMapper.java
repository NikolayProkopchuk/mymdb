package com.prokopchuk.mymdb.adapter.out.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserEntity;
import com.prokopchuk.mymdb.domain.User;

@Mapper()
public interface UserUserEntityMapper {

    User userEntityToUser(UserEntity userEntity);

    @Mapping(target = "roles", ignore = true)
    UserEntity userToUserEntity(User user);

}

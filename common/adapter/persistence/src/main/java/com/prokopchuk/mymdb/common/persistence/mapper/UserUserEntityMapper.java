package com.prokopchuk.mymdb.common.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.common.persistence.entity.UserEntity;
import com.prokopchuk.mymdb.user.domain.User;

@Mapper(uses = {RoleEntityRoleMapper.class, BaseIdToLongMapper.class})
public interface UserUserEntityMapper {

    User userEntityToUser(UserEntity userEntity);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity userToUserEntity(User user);

}

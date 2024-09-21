package com.prokopchuk.mymdb.common.persistence.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

import com.prokopchuk.mymdb.common.persistence.entity.UserEntity;
import com.prokopchuk.mymdb.user.domain.User;

@Mapper(uses = {RoleEntityRoleMapper.class, BaseIdToLongMapper.class})
public interface UserUserEntityMapper extends Converter<User, UserEntity> {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserEntity convert(User user);

    @InheritInverseConfiguration
    @DelegatingConverter
    User invertConvert(UserEntity userEntity);
}

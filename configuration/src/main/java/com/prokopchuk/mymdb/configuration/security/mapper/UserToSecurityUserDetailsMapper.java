package com.prokopchuk.mymdb.configuration.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.prokopchuk.mymdb.common.persistence.mapper.BaseIdToLongMapper;
import com.prokopchuk.mymdb.configuration.security.model.SecurityUserDetails;
import com.prokopchuk.mymdb.user.domain.User;

@Mapper(uses = {BaseIdToLongMapper.class, RoleToSecurityRoleMapper.class})
public interface UserToSecurityUserDetailsMapper {

    @Mapping(target = "authorities", source = "roles")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    SecurityUserDetails userToUserDetailsDto(User user);
}

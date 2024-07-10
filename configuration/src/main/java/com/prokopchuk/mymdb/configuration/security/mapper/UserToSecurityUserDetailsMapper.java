package com.prokopchuk.mymdb.configuration.security.mapper;

import com.prokopchuk.mymdb.common.persistence.mapper.BaseIdToLongMapper;
import com.prokopchuk.mymdb.configuration.security.model.SecurityUserDetails;
import com.prokopchuk.mymdb.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {BaseIdToLongMapper.class, RoleToSecurityRoleMapper.class})
public interface UserToSecurityUserDetailsMapper {

    @Mapping(target = "authorities", source = "roles")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    SecurityUserDetails userToUserDetailsDto(User user);
}

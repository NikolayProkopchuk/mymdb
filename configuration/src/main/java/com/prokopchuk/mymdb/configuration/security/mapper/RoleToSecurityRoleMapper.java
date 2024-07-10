package com.prokopchuk.mymdb.configuration.security.mapper;

import com.prokopchuk.mymdb.configuration.security.model.SecurityRole;
import com.prokopchuk.mymdb.user.domain.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleToSecurityRoleMapper {

    SecurityRole roleToSecurityRole(Role role);
}

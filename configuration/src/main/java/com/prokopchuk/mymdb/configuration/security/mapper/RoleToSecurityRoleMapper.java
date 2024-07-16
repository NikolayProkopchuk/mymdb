package com.prokopchuk.mymdb.configuration.security.mapper;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.configuration.security.model.SecurityRole;
import com.prokopchuk.mymdb.user.domain.Role;

@Mapper
public interface RoleToSecurityRoleMapper {

    SecurityRole roleToSecurityRole(Role role);
}

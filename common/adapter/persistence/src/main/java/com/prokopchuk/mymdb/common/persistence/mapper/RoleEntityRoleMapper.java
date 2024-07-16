package com.prokopchuk.mymdb.common.persistence.mapper;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.common.persistence.entity.RoleEntity;
import com.prokopchuk.mymdb.user.domain.Role;

@Mapper
public interface RoleEntityRoleMapper {

    default Role roleEntityToRole(RoleEntity roleEntity) {
        return roleEntity.getName();
    }

}

package com.prokopchuk.mymdb.adapter.out.persistence.mapper;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.RoleEntity;
import com.prokopchuk.mymdb.domain.Role;

@Mapper
public interface RoleEntityRoleMapper {

    default Role roleEntityToRole(RoleEntity roleEntity) {
        return roleEntity.getName();
    }

}

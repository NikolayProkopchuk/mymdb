package com.prokopchuk.mymdb.common.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.common.persistence.entity.RoleEntity;
import com.prokopchuk.mymdb.user.domain.Role;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(Role name);
}

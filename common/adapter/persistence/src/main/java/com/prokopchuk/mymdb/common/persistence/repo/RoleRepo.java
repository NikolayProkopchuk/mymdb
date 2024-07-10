package com.prokopchuk.mymdb.common.persistence.repo;

import com.prokopchuk.mymdb.common.persistence.entity.RoleEntity;
import com.prokopchuk.mymdb.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(Role name);
}

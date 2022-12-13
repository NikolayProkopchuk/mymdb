package com.prokopchuk.mymdb.adapter.out.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.RoleEntity;
import com.prokopchuk.mymdb.domain.Role;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(Role name);
}

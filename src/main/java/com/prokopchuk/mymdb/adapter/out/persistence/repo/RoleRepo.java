package com.prokopchuk.mymdb.adapter.out.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.RoleEntity;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);
}

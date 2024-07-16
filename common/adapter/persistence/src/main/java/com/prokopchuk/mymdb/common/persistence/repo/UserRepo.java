package com.prokopchuk.mymdb.common.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.common.persistence.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);

    Optional<UserEntity> findUserEntityByEmail(String email);
}

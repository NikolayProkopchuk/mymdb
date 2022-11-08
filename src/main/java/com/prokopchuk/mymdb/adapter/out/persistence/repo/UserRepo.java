package com.prokopchuk.mymdb.adapter.out.persistence.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserEntity;


public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
}

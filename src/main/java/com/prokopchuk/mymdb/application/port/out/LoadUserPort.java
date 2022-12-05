package com.prokopchuk.mymdb.application.port.out;

import java.util.Optional;

import com.prokopchuk.mymdb.domain.User;

public interface LoadUserPort {

    Optional<User> loadUserByUsername(String username);

    Optional<User> loadUserByEmail(String email);
}

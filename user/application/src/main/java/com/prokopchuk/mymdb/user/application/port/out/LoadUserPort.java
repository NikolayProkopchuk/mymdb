package com.prokopchuk.mymdb.user.application.port.out;

import java.util.Optional;

import com.prokopchuk.mymdb.user.domain.User;

public interface LoadUserPort {

    Optional<User> loadUserByUsername(String username);

    Optional<User> loadUserByEmail(String email);
}

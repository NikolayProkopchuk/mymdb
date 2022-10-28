package com.prokopchuk.mymdb.application.port.out;

import com.prokopchuk.mymdb.domain.User;

public interface LoadUserPort {
    User loadUser(Long id);
}

package com.prokopchuk.mymdb.application.port.out;

import com.prokopchuk.mymdb.domain.User;

public interface RegisterUserPort {

    User registerUser(User user);
}

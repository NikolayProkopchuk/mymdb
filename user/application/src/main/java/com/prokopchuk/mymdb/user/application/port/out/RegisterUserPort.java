package com.prokopchuk.mymdb.user.application.port.out;

import com.prokopchuk.mymdb.user.domain.User;

public interface RegisterUserPort {

    User registerUser(User user);
}

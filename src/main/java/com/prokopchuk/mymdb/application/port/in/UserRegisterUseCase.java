package com.prokopchuk.mymdb.application.port.in;

import com.prokopchuk.mymdb.application.port.in.command.RegisterUserCommand;

public interface UserRegisterUseCase {
    String registerUser(RegisterUserCommand userCommand);
}

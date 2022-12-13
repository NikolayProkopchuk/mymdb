package com.prokopchuk.mymdb.application.port.in;

public interface UserRegisterUseCase {
    Long registerUser(RegisterUserCommand userCommand);
}

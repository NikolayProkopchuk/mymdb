package com.prokopchuk.mymdb.user.application.port.in;

import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;

public interface UserRegisterUseCase {
    UserId registerUser(RegisterUserCommand userCommand);
}

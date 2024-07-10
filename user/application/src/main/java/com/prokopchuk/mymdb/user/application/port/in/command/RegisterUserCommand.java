package com.prokopchuk.mymdb.user.application.port.in.command;

import java.time.LocalDate;

import com.prokopchuk.mymdb.user.domain.Sex;

public record RegisterUserCommand(
        String username,
        String email,
        String password,
        Sex sex,
        String firstName,
        String lastName,
        LocalDate birthday
) {
}

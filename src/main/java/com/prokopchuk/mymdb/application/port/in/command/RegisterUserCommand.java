package com.prokopchuk.mymdb.application.port.in.command;

import java.time.LocalDate;

import com.prokopchuk.mymdb.domain.Sex;

public record RegisterUserCommand(
  String username, String email, String password, Sex sex, String firstName, String lastName, LocalDate birthday
) {
}

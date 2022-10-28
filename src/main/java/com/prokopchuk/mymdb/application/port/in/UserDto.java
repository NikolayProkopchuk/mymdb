package com.prokopchuk.mymdb.application.port.in;

import java.time.LocalDate;

import com.prokopchuk.mymdb.domain.Sex;

public record UserDto(String userName,
                      String email,
                      String password,
                      Sex sex,
                      String firstName,
                      String lastName,
                      LocalDate birthday) {
}

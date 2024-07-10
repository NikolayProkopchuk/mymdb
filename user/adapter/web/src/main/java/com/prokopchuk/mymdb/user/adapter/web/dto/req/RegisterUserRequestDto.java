package com.prokopchuk.mymdb.user.adapter.web.dto.req;

import java.time.LocalDate;

import com.prokopchuk.mymdb.user.domain.Sex;

public record RegisterUserRequestDto(String username,
                                     String email,
                                     String password,
                                     Sex sex,
                                     String firstName,
                                     String lastName,
                                     LocalDate birthday) {
}

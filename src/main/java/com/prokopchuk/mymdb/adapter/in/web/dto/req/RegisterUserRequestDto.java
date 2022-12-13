package com.prokopchuk.mymdb.adapter.in.web.dto.req;

import java.time.LocalDate;

import com.prokopchuk.mymdb.domain.Sex;

public record RegisterUserRequestDto(String username, String email, String password, Sex sex, String firstName,
                                     String lastName, LocalDate birthday) {
}

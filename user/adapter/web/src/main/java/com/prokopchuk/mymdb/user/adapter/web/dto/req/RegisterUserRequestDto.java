package com.prokopchuk.mymdb.user.adapter.web.dto.req;

import java.time.LocalDate;

import com.prokopchuk.mymdb.user.domain.Sex;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequestDto(@NotBlank String username,
                                     @NotBlank @Email String email,
                                     @NotBlank String password,
                                     @NotNull Sex sex,
                                     @NotBlank String firstName,
                                     @NotBlank String lastName,
                                     @NotNull LocalDate birthday) {
}

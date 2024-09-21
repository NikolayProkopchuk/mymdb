package com.prokopchuk.mymdb.user.application.port.in.command;

import java.time.LocalDate;

import com.prokopchuk.mymdb.common.application.SelfValidation;
import com.prokopchuk.mymdb.user.domain.Sex;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class RegisterUserCommand extends SelfValidation<RegisterUserCommand> {

    @NotBlank
    String username;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;

    @NotNull
    Sex sex;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotNull
    LocalDate birthday;

    public RegisterUserCommand(String username,
                               String email,
                               String password,
                               Sex sex,
                               String firstName,
                               String lastName,
                               LocalDate birthday) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        validateSelf();
    }
}

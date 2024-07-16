package com.prokopchuk.mymdb.media.application.port.in.command;

import java.time.LocalDate;

import com.prokopchuk.mymdb.common.application.SelfValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateFilmCommand extends SelfValidation<CreateFilmCommand> {

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    String description;

    @NotNull
    LocalDate productionDate;

    public CreateFilmCommand(String name, String description, LocalDate productionDate) {
        this.name = name;
        this.description = description;
        this.productionDate = productionDate;
        validateSelf();
    }
}

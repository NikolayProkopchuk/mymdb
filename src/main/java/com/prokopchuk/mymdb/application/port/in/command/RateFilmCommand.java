package com.prokopchuk.mymdb.application.port.in.command;

import com.prokopchuk.mymdb.common.SelfValidation;
import com.prokopchuk.mymdb.domain.User;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RateFilmCommand extends SelfValidation<RateFilmCommand> {

    @NotNull
    @Positive
    private final Long filmId;

    @NotNull
    private final User user;

    @NotNull
    @Min(0)
    @Max(10)
    private final Integer rating;

    public RateFilmCommand(Long filmId, User user, Integer rating) {
        this.filmId = filmId;
        this.user = user;
        this.rating = rating;

        validateSelf();
    }
}

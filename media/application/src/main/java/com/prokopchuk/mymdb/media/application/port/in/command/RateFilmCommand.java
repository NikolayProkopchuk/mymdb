package com.prokopchuk.mymdb.media.application.port.in.command;

import com.prokopchuk.mymdb.common.application.SelfValidation;
import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RateFilmCommand extends SelfValidation<RateFilmCommand> {

    @NotNull
    private final FilmId filmId;

    @NotNull
    private final UserId userId;

    @NotNull
    @Min(0)
    @Max(10)
    private final Integer rating;

    public RateFilmCommand(FilmId filmId, UserId userId, Integer rating) {
        this.filmId = filmId;
        this.userId = userId;
        this.rating = rating;

        validateSelf();
    }
}

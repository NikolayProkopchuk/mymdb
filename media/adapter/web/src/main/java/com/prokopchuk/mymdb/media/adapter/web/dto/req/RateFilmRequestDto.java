package com.prokopchuk.mymdb.media.adapter.web.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RateFilmRequestDto(@NotNull Long filmId,
                                 @NotNull @Min(0) @Max(10) Integer rating) {
}

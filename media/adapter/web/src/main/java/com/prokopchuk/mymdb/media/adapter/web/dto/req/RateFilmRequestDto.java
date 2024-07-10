package com.prokopchuk.mymdb.media.adapter.web.dto.req;

import jakarta.validation.constraints.NotNull;

public record RateFilmRequestDto(@NotNull Long filmId, @NotNull Integer rating) {
}

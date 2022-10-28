package com.prokopchuk.mymdb.application.port.in;

import com.prokopchuk.mymdb.domain.UserRating;

public interface RateFilmUseCase {
    UserRating rate(Long filmId, Long userId, int value);
}

package com.prokopchuk.mymdb.media.domain;

import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;

public record UserRating(UserId userId, FilmId filmId, Rating rating) {
}

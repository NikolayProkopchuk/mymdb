package com.prokopchuk.mymdb.media.application.port.in;

import com.prokopchuk.mymdb.media.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.media.domain.UserRating;

public interface RateFilmUseCase {
    UserRating rate(RateFilmCommand rateFilmCommand);
}

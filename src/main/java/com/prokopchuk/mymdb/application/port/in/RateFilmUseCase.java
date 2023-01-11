package com.prokopchuk.mymdb.application.port.in;

import com.prokopchuk.mymdb.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.domain.UserRating;

public interface RateFilmUseCase {
    UserRating rate(RateFilmCommand rateFilmCommand);
}

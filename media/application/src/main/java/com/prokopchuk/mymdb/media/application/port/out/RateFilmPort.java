package com.prokopchuk.mymdb.media.application.port.out;

import com.prokopchuk.mymdb.media.domain.UserRating;

public interface RateFilmPort {

    UserRating rate(UserRating userRating);

}

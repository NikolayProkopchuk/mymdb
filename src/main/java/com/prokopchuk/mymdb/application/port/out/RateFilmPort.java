package com.prokopchuk.mymdb.application.port.out;

import com.prokopchuk.mymdb.domain.UserRating;

public interface RateFilmPort {

    UserRating rate(UserRating userRating);

}

package com.prokopchuk.mymdb.media.application.port.out;

import com.prokopchuk.mymdb.media.domain.Film;

public interface CreateFilmPort {

    Film createFilm(Film film);
}

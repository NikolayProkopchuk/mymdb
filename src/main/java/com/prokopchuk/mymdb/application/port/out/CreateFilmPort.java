package com.prokopchuk.mymdb.application.port.out;

import com.prokopchuk.mymdb.domain.Film;

public interface CreateFilmPort {

    Film createFilm(Film film);
}

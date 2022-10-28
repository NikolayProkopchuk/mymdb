package com.prokopchuk.mymdb.application.port.in;

import com.prokopchuk.mymdb.domain.Film;

public interface CreateOrUpdateFilmUseCase {
    Film createOrUpdateFilm(FilmDto filmDto);
}

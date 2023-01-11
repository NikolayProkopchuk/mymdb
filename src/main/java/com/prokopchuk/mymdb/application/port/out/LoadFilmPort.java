package com.prokopchuk.mymdb.application.port.out;

import java.util.Optional;

import com.prokopchuk.mymdb.domain.Film;

public interface LoadFilmPort {

    Optional<Film> loadFilmById(Long id);
}

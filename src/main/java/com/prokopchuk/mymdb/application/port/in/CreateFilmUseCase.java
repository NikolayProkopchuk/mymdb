package com.prokopchuk.mymdb.application.port.in;

import com.prokopchuk.mymdb.application.port.in.command.CreateFilmCommand;

public interface CreateFilmUseCase {

    Long createFilm(CreateFilmCommand createFilmCommand);
}

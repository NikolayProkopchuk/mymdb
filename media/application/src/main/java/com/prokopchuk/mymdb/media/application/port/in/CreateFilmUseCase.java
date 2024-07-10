package com.prokopchuk.mymdb.media.application.port.in;

import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;

public interface CreateFilmUseCase {

    Long createFilm(CreateFilmCommand createFilmCommand);
}

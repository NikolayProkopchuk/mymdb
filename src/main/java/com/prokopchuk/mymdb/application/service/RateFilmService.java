package com.prokopchuk.mymdb.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.prokopchuk.mymdb.application.port.in.RateFilmUseCase;
import com.prokopchuk.mymdb.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.application.port.out.LoadFilmPort;
import com.prokopchuk.mymdb.application.port.out.RateFilmPort;
import com.prokopchuk.mymdb.common.annotation.UseCase;
import com.prokopchuk.mymdb.domain.Rating;
import com.prokopchuk.mymdb.domain.UserRating;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RateFilmService implements RateFilmUseCase {

    private final RateFilmPort rateFilmPort;

    private final LoadFilmPort loadFilmPort;

    @Override
    @Transactional
    public UserRating rate(RateFilmCommand rateFilmCommand) {
        Rating rating = Rating.getInstance(rateFilmCommand.getRating());
        var film = loadFilmPort.loadFilmById(rateFilmCommand.getFilmId()).orElseThrow();
        UserRating userRating = new UserRating(
          rateFilmCommand.getUser(),
          film,
          rating
        );

        return rateFilmPort.rate(userRating);
    }
}

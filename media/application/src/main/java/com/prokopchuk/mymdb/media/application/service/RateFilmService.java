package com.prokopchuk.mymdb.media.application.service;

import org.springframework.transaction.annotation.Transactional;

import com.prokopchuk.mymdb.common.application.annotation.UseCase;
import com.prokopchuk.mymdb.media.application.port.in.RateFilmUseCase;
import com.prokopchuk.mymdb.media.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.media.application.port.out.LoadFilmPort;
import com.prokopchuk.mymdb.media.application.port.out.RateFilmPort;
import com.prokopchuk.mymdb.media.domain.UserRating;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RateFilmService implements RateFilmUseCase {

    private final RateFilmPort rateFilmPort;

    private final LoadFilmPort loadFilmPort;

    @Override
    @Transactional
    public UserRating rate(RateFilmCommand rateFilmCommand) {
        loadFilmPort.loadFilmById(rateFilmCommand.getFilmId().getValue())
                .orElseThrow();
        UserRating userRating = new UserRating(
          rateFilmCommand.getUserId(),
          rateFilmCommand.getFilmId(),
          rateFilmCommand.getRating()
        );

        return rateFilmPort.rate(userRating);
    }
}

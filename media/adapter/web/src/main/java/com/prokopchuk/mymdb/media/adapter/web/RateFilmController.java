package com.prokopchuk.mymdb.media.adapter.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prokopchuk.mymdb.common.adapter.web.annotation.AuthenticationUserId;
import com.prokopchuk.mymdb.common.adapter.web.annotation.WebAdapter;
import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.media.adapter.web.dto.req.RateFilmRequestDto;
import com.prokopchuk.mymdb.media.application.port.in.RateFilmUseCase;
import com.prokopchuk.mymdb.media.application.port.in.command.RateFilmCommand;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RateFilmController {

    private final RateFilmUseCase rateFilmUseCase;

    @PostMapping("/ratings")
    public void rateFilm(@RequestBody @Validated RateFilmRequestDto rateFilmRequestDto,
                         @AuthenticationUserId Long userId) {

        var rateFilmCommand = new RateFilmCommand(
                new FilmId(rateFilmRequestDto.filmId()),
                new UserId(userId),
                rateFilmRequestDto.rating());

        rateFilmUseCase.rate(rateFilmCommand);
    }
}

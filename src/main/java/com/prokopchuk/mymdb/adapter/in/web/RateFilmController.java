package com.prokopchuk.mymdb.adapter.in.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prokopchuk.mymdb.adapter.in.web.dto.req.RateFilmRequestDto;
import com.prokopchuk.mymdb.application.port.in.RateFilmUseCase;
import com.prokopchuk.mymdb.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.common.annotation.WebAdapter;
import com.prokopchuk.mymdb.domain.User;

import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RateFilmController {

    private final RateFilmUseCase rateFilmUseCase;

    @PostMapping("/ratings")
    public void rateFilm(@RequestBody RateFilmRequestDto rateFilmRequestDto,
                         @AuthenticationPrincipal User user) {

        var rateFilmCommand = new RateFilmCommand(
          rateFilmRequestDto.filmId(),
          user,
          rateFilmRequestDto.rating());

        rateFilmUseCase.rate(rateFilmCommand);
    }
}

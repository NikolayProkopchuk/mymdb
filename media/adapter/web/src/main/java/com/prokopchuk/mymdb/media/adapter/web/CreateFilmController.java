package com.prokopchuk.mymdb.media.adapter.web;


import com.prokopchuk.mymdb.common.adapter.web.annotation.WebAdapter;
import com.prokopchuk.mymdb.media.adapter.web.dto.req.CreateFilmRequestDto;
import com.prokopchuk.mymdb.media.adapter.web.mapper.CreateFilmRequestToCommandMapper;
import com.prokopchuk.mymdb.media.application.port.in.CreateFilmUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
class CreateFilmController {

    private final CreateFilmUseCase createFilmUseCase;

    private final CreateFilmRequestToCommandMapper createFilmRequestToCommandMapper;

    @PostMapping
    public ResponseEntity<Long> createFilm(@RequestBody CreateFilmRequestDto dto) {

        var createFilmCommand = createFilmRequestToCommandMapper.requestToCommand(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
          .body(createFilmUseCase.createFilm(createFilmCommand));
    }
}

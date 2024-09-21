package com.prokopchuk.mymdb.media.adapter.web;


import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prokopchuk.mymdb.common.adapter.web.annotation.WebAdapter;
import com.prokopchuk.mymdb.media.adapter.web.dto.req.CreateFilmRequestDto;
import com.prokopchuk.mymdb.media.application.port.in.CreateFilmUseCase;
import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
class CreateFilmController {

    private final CreateFilmUseCase createFilmUseCase;

    private final ConversionService conversionService;

    @PostMapping
    public ResponseEntity<Void> createFilm(@RequestBody @Valid CreateFilmRequestDto dto) {

        CreateFilmCommand createFilmCommand = conversionService.convert(dto, CreateFilmCommand.class);

        Long filmId = createFilmUseCase.createFilm(createFilmCommand);

        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(filmId)
              .toUri())
          .build();
    }
}

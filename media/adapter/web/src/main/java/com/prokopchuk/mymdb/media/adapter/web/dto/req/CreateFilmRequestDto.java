package com.prokopchuk.mymdb.media.adapter.web.dto.req;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFilmRequestDto(
  @NotBlank String name,
  @NotBlank String description,
  @NotNull LocalDate productionDate) {
}

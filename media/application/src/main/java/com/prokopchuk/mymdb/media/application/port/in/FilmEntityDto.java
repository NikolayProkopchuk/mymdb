package com.prokopchuk.mymdb.media.application.port.in;

import java.time.LocalDate;

public record FilmEntityDto(
  Long id,
  String name,
  String description,
  LocalDate productionDate,
  Double rating
) {
}

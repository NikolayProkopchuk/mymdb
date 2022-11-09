package com.prokopchuk.mymdb.adapter.out.persistence.entity;

import java.time.LocalDate;

public record FilmEntityDto(
  Long id,
  String name,
  String description,
  LocalDate productionDate,
  Double rating
) {
}

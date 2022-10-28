package com.prokopchuk.mymdb.application.port.in;

import java.time.LocalDate;

public record FilmDto(Long id,
                      String name,
                      String description,
                      LocalDate productionDate) {
}

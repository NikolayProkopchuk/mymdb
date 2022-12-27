package com.prokopchuk.mymdb.adapter.in.web.dto.req;

import java.time.LocalDate;

public record CreateFilmRequestDto(String name, String description, LocalDate productionDate) {

}

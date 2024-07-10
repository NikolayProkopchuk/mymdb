package com.prokopchuk.mymdb.media.adapter.web.dto.req;

import java.time.LocalDate;

public record CreateFilmRequestDto(String name, String description, LocalDate productionDate) {

}

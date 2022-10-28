package com.prokopchuk.mymdb.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Film {
    private Long id;

    private String name;

    private String description;

    private LocalDate productionDate;

    private MymdbRating mymdbRating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

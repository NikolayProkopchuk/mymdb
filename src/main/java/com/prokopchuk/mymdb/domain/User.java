package com.prokopchuk.mymdb.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String userName;

    private String email;

    private Sex sex;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

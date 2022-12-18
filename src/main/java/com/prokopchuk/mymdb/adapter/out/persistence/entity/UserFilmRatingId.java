package com.prokopchuk.mymdb.adapter.out.persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserFilmRatingId implements Serializable {

    private Long userId;

    private Long filmId;
}

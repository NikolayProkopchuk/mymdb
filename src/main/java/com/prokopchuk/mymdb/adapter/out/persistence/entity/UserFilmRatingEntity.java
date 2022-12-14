package com.prokopchuk.mymdb.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users_films_rating")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserFilmRatingEntity  {

    @EmbeddedId
    private UserFilmRatingId userFilmRatingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "film_id")
    @MapsId("filmId")
    private FilmEntity film;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        UserFilmRatingEntity that = (UserFilmRatingEntity) obj;
        return userFilmRatingId != null && Objects.equals(userFilmRatingId, that.userFilmRatingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userFilmRatingId);
    }
}

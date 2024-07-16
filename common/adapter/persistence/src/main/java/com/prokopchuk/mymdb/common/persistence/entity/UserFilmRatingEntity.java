package com.prokopchuk.mymdb.common.persistence.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.Hibernate;

import com.prokopchuk.mymdb.media.domain.Rating;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "media", name = "users_films_rating")
@Getter
@Setter
@ToString
public class UserFilmRatingEntity  {

    @EmbeddedId
    private UserFilmRatingId userFilmRatingId = new UserFilmRatingId();

    @ManyToOne(optional = false)
    @MapsId("userId")
    private UserEntity user;

    @ManyToOne(optional = false)
    @MapsId("filmId")
    private FilmEntity film;

    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

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

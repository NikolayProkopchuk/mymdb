package com.prokopchuk.mymdb.adapter.out.persistence.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.Hibernate;

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

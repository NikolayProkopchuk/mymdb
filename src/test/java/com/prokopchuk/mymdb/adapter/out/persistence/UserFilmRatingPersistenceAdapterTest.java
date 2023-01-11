package com.prokopchuk.mymdb.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.prokopchuk.mymdb.adapter.out.persistence.adapter.UserFilmRatingPersistenceAdapter;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.UserFilmRatingRepo;
import com.prokopchuk.mymdb.domain.Film;
import com.prokopchuk.mymdb.domain.Rating;
import com.prokopchuk.mymdb.domain.User;
import com.prokopchuk.mymdb.domain.UserRating;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserFilmRatingPersistenceAdapter.class)
@Testcontainers
@ActiveProfiles("test-flyway")
class UserFilmRatingPersistenceAdapterTest {

    @Autowired
    private UserFilmRatingPersistenceAdapter userFilmRatingPersistenceAdapter;

    @Autowired
    private UserFilmRatingRepo userFilmRatingRepo;

    @AfterEach
    void init() {
        userFilmRatingRepo.deleteAll();
    }

    @Test
    @Sql("UserFilmRatingPersistenceAdapterTest.sql")
    void rateFilm() {
        User user = User.builder()
          .id(100L)
          .username("test")
            .build();
        Film film = Film.builder()
            .id(200L)
              .build();
        UserRating userRating = new UserRating(
          user,
          film,
          Rating.EIGHT
        );
        userFilmRatingPersistenceAdapter.rate(userRating);

        var userFilmRatingEntity = userFilmRatingRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).get(0);

        assertThat(userFilmRatingEntity.getRating()).isEqualTo(Rating.EIGHT);
        assertThat(userFilmRatingEntity.getUser().getUsername()).isEqualTo("test");
        assertThat(userFilmRatingEntity.getFilm().getId()).isEqualTo(200L);
    }

}

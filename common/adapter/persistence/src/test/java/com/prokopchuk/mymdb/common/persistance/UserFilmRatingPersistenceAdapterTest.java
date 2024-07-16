package com.prokopchuk.mymdb.common.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.common.persistance.config.TestConfig;
import com.prokopchuk.mymdb.common.persistence.UserFilmRatingPersistenceAdapter;
import com.prokopchuk.mymdb.common.persistence.repo.UserFilmRatingRepo;
import com.prokopchuk.mymdb.media.domain.Rating;
import com.prokopchuk.mymdb.media.domain.UserRating;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserFilmRatingPersistenceAdapter.class)
@ContextConfiguration(classes = TestConfig.class)
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
        UserId userId = new UserId(100L);
        FilmId filmId = new FilmId(200L);
        UserRating userRating = new UserRating(
          userId,
          filmId,
          Rating.EIGHT
        );
        userFilmRatingPersistenceAdapter.rate(userRating);

        var userFilmRatingEntity = userFilmRatingRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .getFirst();

        assertThat(userFilmRatingEntity.getRating()).isEqualTo(Rating.EIGHT);
        assertThat(userFilmRatingEntity.getUser().getUsername()).isEqualTo("test");
        assertThat(userFilmRatingEntity.getFilm().getId()).isEqualTo(200L);
    }

}

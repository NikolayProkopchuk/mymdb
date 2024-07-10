package com.prokopchuk.mymdb.common.persistance;

import com.prokopchuk.mymdb.common.persistance.config.TestConfig;
import com.prokopchuk.mymdb.common.persistence.FilmPersistenceAdapter;
import com.prokopchuk.mymdb.media.domain.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(FilmPersistenceAdapter.class)
@ContextConfiguration(classes = TestConfig.class)
@Testcontainers
@ActiveProfiles("test-flyway")
class FilmPersistenceAdapterTest {

    @Autowired
    private FilmPersistenceAdapter filmPersistenceAdapter;

    @Test
    void createFilm() {
        Film film = Film.builder()
          .name("testFilm")
          .description("test description")
          .productionDate(LocalDate.of(2000, 1, 1))
          .build();

        var createdFilm = filmPersistenceAdapter.createFilm(film);

        assertThat(createdFilm.getName()).isEqualTo(film.getName());
        assertThat(createdFilm.getDescription()).isEqualTo(film.getDescription());
        assertThat(createdFilm.getProductionDate()).isEqualTo(film.getProductionDate());
    }

    @Test
    @Sql("FilmPersistenceAdapterTest.sql")
    void loadFilmById() {
        Film film = filmPersistenceAdapter.loadFilmById(100L).orElseThrow();
        assertThat(film.getName()).isEqualTo("test name");
        assertThat(film.getDescription()).isEqualTo("test description");
        assertThat(film.getProductionDate()).isEqualTo("2000-01-01");
        assertThat(film.getCreatedAt()).isNotNull();
    }
}

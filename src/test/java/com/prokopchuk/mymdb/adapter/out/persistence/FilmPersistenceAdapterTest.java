package com.prokopchuk.mymdb.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.prokopchuk.mymdb.domain.Film;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(FilmPersistenceAdapter.class)
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
}

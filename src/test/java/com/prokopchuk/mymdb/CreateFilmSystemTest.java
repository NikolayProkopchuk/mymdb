package com.prokopchuk.mymdb;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.FilmRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test-flyway")
class CreateFilmSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FilmRepo filmRepo;

    private final String name = "test name";
    private final String description = "test description";

    private final LocalDate productionDate = LocalDate.of(2000, 1, 1);

    @Test
    void createFilm() {
        var resp = whenCreateFilm();
        FilmEntity filmEntity = filmRepo.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);
        then(resp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        then(resp.getBody()).isEqualTo(filmEntity.getId());
        then(filmEntity.getName()).isEqualTo(name);
        then(filmEntity.getDescription()).isEqualTo(description);
        then(filmEntity.getProductionDate()).isEqualTo(productionDate);
    }

    private ResponseEntity<Long> whenCreateFilm() {
        ResponseEntity<Void> response = performLoginRequest();

        String validRequestBody = String.format("""
          {
          "name" : "%s",
          "description" : "%s",
          "productionDate" : "%s"
          }
          """, name, description, productionDate);
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        var request = new HttpEntity<>(validRequestBody, headers);
        return restTemplate
          .postForEntity("/films", request, Long.class);
    }

    private ResponseEntity<Void> performLoginRequest() {
        String loginRequestBody = """
          {
          "username" : "admin",
          "password" : "adminStrongPassword"
          }
          """;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var request = new HttpEntity<>(loginRequestBody, headers);

        return restTemplate.postForEntity("/users/login", request, Void.class);
    }

    @ParameterizedTest
    @MethodSource("argsProvider")
    void createFilmReturns400WhenSomeFieldsAreEmpty(String name, String description, LocalDate productionDate) {
        var resp = whenCreateFilmWithNotValidRequest(name, description, productionDate);
        then(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    static Stream<Arguments> argsProvider() {
        return Stream.of(
          arguments("", "test description", LocalDate.of(2000, 1, 1)),
          arguments("test name", "", LocalDate.of(2000, 1, 1)),
          arguments("test name", "test description", null)
        );
    }

    private ResponseEntity<String> whenCreateFilmWithNotValidRequest(
      String name, String description, LocalDate productionDate) {
        ResponseEntity<Void> response = performLoginRequest();

        var headers = new HttpHeaders();

        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        String notValidRequestBody = String.format("""
          {
          "name" : "%s",
          "description" : "%s",
          "productionDate" : "%s"
          }
          """, name, description, Objects.toString(productionDate, ""));

        var request = new HttpEntity<>(notValidRequestBody, headers);
        return restTemplate
          .postForEntity("/films", request, String.class);
    }
}

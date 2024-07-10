package com.prokopchuk.mymdb.user.adapter.web;

import com.prokopchuk.mymdb.common.persistence.repo.UserRepo;
import com.prokopchuk.mymdb.configuration.MymdbApplication;
import com.prokopchuk.mymdb.user.domain.Sex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MymdbApplication.class)
@Testcontainers
@ActiveProfiles("test-flyway")
class UserRegisterSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepo userRepo;

    @AfterEach
    void cleanup() {
        userRepo.findUserEntityByUsername("test")
          .ifPresent(e -> userRepo.delete(e));
    }

    @Test
    void registerUser() {
        var response = whenRegisterUser();
        var userEntity = userRepo.findUserEntityByUsername("test").orElseThrow();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userEntity.getEmail()).isEqualTo("test@mail.com");
        assertThat(userEntity.getFirstName()).isEqualTo("testFirstName");
        assertThat(userEntity.getLastName()).isEqualTo("testLastName");
        assertThat(userEntity.getSex()).isEqualTo(Sex.MALE);
        assertThat(userEntity.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 1));
    }

    private ResponseEntity<String> whenRegisterUser() {
        String requestBody = """
          {
          "username": "test",
          "email": "test@mail.com",
          "password": "testpass",
          "sex": "MALE",
          "firstName": "testFirstName",
          "lastName": "testLastName",
          "birthday": "2000-01-01"
          }
          """;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        var request = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity("/users", request, String.class);
    }
}

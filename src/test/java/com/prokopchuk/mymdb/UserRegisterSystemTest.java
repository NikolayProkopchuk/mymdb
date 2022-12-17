package com.prokopchuk.mymdb;

import static org.assertj.core.api.Assertions.assertThat;

import com.prokopchuk.mymdb.adapter.out.persistence.repo.UserRepo;
import com.prokopchuk.mymdb.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.domain.User;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test-flyway")
class UserRegisterSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LoadUserPort loadUserPort;

    @Autowired
    private UserRepo userRepo;

    @AfterEach
    void init() {
        userRepo.deleteAll();
    }

    @Test
    void registerUser() {
        var response = whenRegisterUser();
        User user = loadUserPort.loadUserByUsername("test").orElseThrow();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(user.getUsername()).isEqualTo("test");
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

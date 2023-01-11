package com.prokopchuk.mymdb;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserFilmRatingId;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.UserFilmRatingRepo;
import com.prokopchuk.mymdb.domain.Rating;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test-flyway")
class RateFilmSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserFilmRatingRepo userFilmRatingRepo;

    @Test
    void rateFilm() {
        String userName = "testUser";
        ResponseEntity<Void> loginResp = performLogin(userName, "testUserStrongPassword");
        ResponseEntity<Void> resp = whenRateFilm(loginResp.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        UserFilmRatingId userFilmRatingId = new UserFilmRatingId(3L, 1L);
        var userFilmRatingEntity = userFilmRatingRepo.findById(userFilmRatingId).orElseThrow();

        then(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(userFilmRatingEntity.getRating()).isEqualTo(Rating.NINE);
        then(userFilmRatingEntity.getUser().getUsername()).isEqualTo(userName);
    }

    private ResponseEntity<Void> performLogin(String username, String password) {
        String loginReqBody = String.format("""
          {
            "username" : "%s",
            "password" : "%s"
          }
          """, username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        var request = new HttpEntity<>(loginReqBody, headers);

        return restTemplate.postForEntity("/users/login", request, Void.class);
    }

    private ResponseEntity<Void> whenRateFilm(String authorizationToken) {
        String reqBody = """
          {
             "filmId" : 1,
             "rating" : 9
          }
          """;
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, authorizationToken);
        var request = new HttpEntity<>(reqBody, headers);
        return restTemplate.postForEntity("/ratings", request, Void.class);
    }
}

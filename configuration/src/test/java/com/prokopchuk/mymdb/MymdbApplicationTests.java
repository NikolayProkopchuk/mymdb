package com.prokopchuk.mymdb;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Objects;

import com.prokopchuk.mymdb.configuration.MymdbApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {MymdbApplication.class})
class MymdbApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        var resp = restTemplate.getForEntity("/actuator/health", JsonNode.class);
        then(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(Objects.requireNonNull(resp.getBody()).get("status").asText())
          .isEqualTo("UP");
    }

}

package com.prokopchuk.mymdb.adapter.in.web;


import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import com.prokopchuk.mymdb.MymdbApplication;
import com.prokopchuk.mymdb.application.port.in.RateFilmUseCase;
import com.prokopchuk.mymdb.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.config.SpringSecurityWebAuxTestConfig;
import com.prokopchuk.mymdb.config.WebSecurityConfig;
import com.prokopchuk.mymdb.domain.Rating;
import com.prokopchuk.mymdb.domain.User;

@WebMvcTest(RateFilmController.class)
@AutoConfigureMockMvc
@SpringJUnitWebConfig(classes = {MymdbApplication.class, WebSecurityConfig.class, SpringSecurityWebAuxTestConfig.class})
class RateFilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateFilmUseCase rateFilmUseCase;

    private final String validRateFilmJson = """
      {
      "filmId" : 1,
      "rating" : 5
      }
      """;

    @Test
    void shouldRejectRateFilmForAnonymous() throws Exception {
        mockMvc.perform(
          post("/ratings")
          .contentType(MediaType.APPLICATION_JSON)
          .content(validRateFilmJson))
          .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "testUser")
    void rateFilm() throws Exception {
        long filmId = 1;
        Rating rating = Rating.FIVE;

        User user = User
          .builder()
          .username("testUser")
          .build();

        RateFilmCommand rateFilmCommand = new RateFilmCommand(
          filmId,
          user,
          rating.getValue()
        );

        mockMvc
          .perform(
          post("/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(validRateFilmJson))
          .andExpect(status().isOk());

        then(rateFilmUseCase).should().rate(rateFilmCommand);
    }

    @ParameterizedTest
    @MethodSource("notValidBodysSource")
    @WithUserDetails(value = "testUser")
    void rateFilmShouldReturn400WithNotValidInput(String notValidBody) throws Exception {
        mockMvc
          .perform(
            post("/ratings")
              .contentType(MediaType.APPLICATION_JSON)
              .content(notValidBody))
          .andExpect(status().isBadRequest());
    }

    static Stream<String> notValidBodysSource() {
        return Stream.of("""
          {
          "filmId" : 1
          }
          """,
          """
          {
          "rating" : 1
          }
          """,
          """
          {}
          """,
          """
          {
          "filmId" : 1,
          "rating" : 11
          }
          """
          );
    }

}

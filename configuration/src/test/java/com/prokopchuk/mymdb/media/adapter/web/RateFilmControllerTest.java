package com.prokopchuk.mymdb.media.adapter.web;


import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import com.prokopchuk.mymdb.common.adapter.web.advice.MymdbExceptionHandler;
import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.configuration.SpringSecurityWebAuxTestConfig;
import com.prokopchuk.mymdb.configuration.WebConfig;
import com.prokopchuk.mymdb.configuration.security.WebSecurityConfig;
import com.prokopchuk.mymdb.media.application.port.in.RateFilmUseCase;
import com.prokopchuk.mymdb.media.application.port.in.command.RateFilmCommand;
import com.prokopchuk.mymdb.media.domain.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RateFilmController.class)
@SpringJUnitWebConfig(classes = {WebConfig.class, WebSecurityConfig.class, SpringSecurityWebAuxTestConfig.class})
@AutoConfigureMockMvc
@Import({RateFilmController.class, MymdbExceptionHandler.class})
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
        FilmId filmId = new FilmId(1L);
        Rating rating = Rating.FIVE;

        UserId userId = new UserId(1L);

        RateFilmCommand rateFilmCommand = new RateFilmCommand(
          filmId,
          userId,
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
    @MethodSource("notValidBodySource")
    @WithUserDetails(value = "testUser")
    void rateFilmShouldReturn400WithNotValidInput(String notValidBody) throws Exception {
        mockMvc
          .perform(
            post("/ratings")
              .contentType(MediaType.APPLICATION_JSON)
              .content(notValidBody))
          .andExpect(status().isBadRequest());
    }

    static Stream<String> notValidBodySource() {
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

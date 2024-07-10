package com.prokopchuk.mymdb.media.adapter.web;

import com.prokopchuk.mymdb.common.adapter.web.advice.MymdbExceptionHandler;
import com.prokopchuk.mymdb.media.adapter.web.dto.req.CreateFilmRequestDto;
import com.prokopchuk.mymdb.media.adapter.web.mapper.CreateFilmRequestToCommandMapper;
import com.prokopchuk.mymdb.media.application.port.in.CreateFilmUseCase;
import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreateFilmController.class)
class CreateFilmControllerTest {

    @Configuration
    @Import({CreateFilmController.class, MymdbExceptionHandler.class})
    public static class TestConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateFilmUseCase createFilmUseCase;

    @MockBean
    private CreateFilmRequestToCommandMapper createFilmRequestToCommandMapper;

    private final String validCreateFilmJson = """
      {
      "name" : "testName",
      "description" : "test description",
      "productionDate" : "2000-01-01"
      }
      """;

    @Test
    void createFilm() throws Exception {
        String name = "testName";
        String description = "test description";
        LocalDate productionDate = LocalDate.of(2000, 1, 1);
        var createFilmDto = new CreateFilmRequestDto(
          name,
          description,
          productionDate
        );
        var createFilmCommand = new CreateFilmCommand(
          name,
          description,
          productionDate);

        given(createFilmRequestToCommandMapper.requestToCommand(createFilmDto))
          .willReturn(createFilmCommand);
        given(createFilmUseCase.createFilm(createFilmCommand))
          .willReturn(1L);

        mockMvc.perform(post("/films")
            .content(validCreateFilmJson)
            .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(content().json("1"));

        then(createFilmRequestToCommandMapper).should().requestToCommand(createFilmDto);
        then(createFilmUseCase).should().createFilm(createFilmCommand);
    }

    @Test
    void createFilmReturns400WhenMapperThrowsConstrainViolationException() throws Exception {
        given(createFilmRequestToCommandMapper.requestToCommand(any()))
          .willThrow(ConstraintViolationException.class);
        mockMvc.perform(
            post("/films")
              .content(validCreateFilmJson)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest());
    }
}

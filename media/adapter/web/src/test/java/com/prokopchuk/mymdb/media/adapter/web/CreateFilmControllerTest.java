package com.prokopchuk.mymdb.media.adapter.web;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.prokopchuk.mymdb.common.adapter.web.advice.MymdbGlobalExceptionHandler;
import com.prokopchuk.mymdb.media.adapter.web.dto.req.CreateFilmRequestDto;
import com.prokopchuk.mymdb.media.application.port.in.CreateFilmUseCase;
import com.prokopchuk.mymdb.media.application.port.in.command.CreateFilmCommand;


@WebMvcTest(CreateFilmController.class)
class CreateFilmControllerTest {

    @Configuration
    @Import({CreateFilmController.class, MymdbGlobalExceptionHandler.class})
    static class TestConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateFilmUseCase createFilmUseCase;

    @SpyBean
    private ConversionService conversionService;

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

        doReturn(createFilmCommand)
          .when(conversionService)
          .convert(createFilmDto, CreateFilmCommand.class);
        given(createFilmUseCase.createFilm(createFilmCommand))
          .willReturn(1L);

        String validCreateFilmJson = """
          {
          "name" : "testName",
          "description" : "test description",
          "productionDate" : "2000-01-01"
          }
          """;
        mockMvc.perform(post("/films")
            .content(validCreateFilmJson)
            .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(header().string(HttpHeaders.LOCATION, endsWith("/films/" + 1)));

        then(conversionService).should().convert(createFilmDto, CreateFilmCommand.class);
        then(createFilmUseCase).should().createFilm(createFilmCommand);
    }

    @Test
    void createFilmReturns400WhenWhenRequestIsNotValid() throws Exception {
        String notValidCreateFilmJson = """
          {
          "description" : "test description",
          "productionDate" : "2000-01-01"
          }
          """;
        mockMvc.perform(
            post("/films")
              .content(notValidCreateFilmJson)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest());
    }
}

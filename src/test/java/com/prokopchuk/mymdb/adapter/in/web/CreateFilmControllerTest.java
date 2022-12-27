package com.prokopchuk.mymdb.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import com.prokopchuk.mymdb.MymdbApplication;
import com.prokopchuk.mymdb.adapter.in.web.dto.req.CreateFilmRequestDto;
import com.prokopchuk.mymdb.adapter.in.web.mapper.CreateFilmRequestToCommandMapper;
import com.prokopchuk.mymdb.application.port.in.CreateFilmUseCase;
import com.prokopchuk.mymdb.application.port.in.command.CreateFilmCommand;
import com.prokopchuk.mymdb.config.WebSecurityConfig;

import jakarta.validation.ConstraintViolationException;

@WebMvcTest(CreateFilmController.class)
@AutoConfigureMockMvc
@SpringJUnitWebConfig(classes = {MymdbApplication.class, WebSecurityConfig.class})
class CreateFilmControllerTest {

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
    void shouldRejectCreatingFilmWhenUserIsAnonymous() throws Exception {
        mockMvc.perform(post("/films")
            .content(validCreateFilmJson)
            .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
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
    @WithMockUser
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

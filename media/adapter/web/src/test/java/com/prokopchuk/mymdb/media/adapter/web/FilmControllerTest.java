package com.prokopchuk.mymdb.media.adapter.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.prokopchuk.mymdb.common.adapter.web.advice.MymdbGlobalExceptionHandler;
import com.prokopchuk.mymdb.media.adapter.web.advice.MediaExceptionHandler;
import com.prokopchuk.mymdb.media.application.port.in.FilmDtoQuery;
import com.prokopchuk.mymdb.media.application.service.exception.FilmNotFoundException;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @Configuration
    @Import({FilmController.class, MymdbGlobalExceptionHandler.class, MediaExceptionHandler.class})
    static class TestConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmDtoQuery filmDtoQuery;


    @Test
    void getFilmReturns404IfFilmNotFound() throws Exception {
        when(filmDtoQuery.getFilm(any()))
          .thenThrow(new FilmNotFoundException(String.format("Film with id: %d not found", 1)));
        mockMvc.perform(get("/films/1"))
          .andExpect(status().isNotFound());
    }
}

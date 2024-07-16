package com.prokopchuk.mymdb.user.adapter.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.adapter.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.user.adapter.web.mapper.UserRequestToCommandMapper;
import com.prokopchuk.mymdb.user.application.exception.UserException;
import com.prokopchuk.mymdb.user.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.domain.Sex;

@WebMvcTest(UserRegisterController.class)
class UserRegisterControllerTest {

    @Configuration
    @Import(UserRegisterController.class)
    public static class TestConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegisterUseCase userRegisterUseCase;

    @MockBean
    private UserRequestToCommandMapper userRequestToCommandMapper;

    @Test
    void registerUserTest() throws Exception {
        var createUserDto = new RegisterUserRequestDto("test", "test@mail.com", "testpass",
          Sex.MALE, "testFirstName", "testLastName", LocalDate.of(2000, 1, 1));
        var createUserCommand = createUserCommand();
        UserId userId = new UserId(1L);
        when(userRequestToCommandMapper.registerUserRequestToCommand(createUserDto)).thenReturn(createUserCommand);
        when(userRegisterUseCase.registerUser(createUserCommand)).thenReturn(userId);
        mockMvc.perform(post("/users")
            .content("""
                {
                "username": "test",
                "email": "test@mail.com",
                "password": "testpass",
                "sex": "MALE",
                "firstName": "testFirstName",
                "lastName": "testLastName",
                "birthday": "2000-01-01"
                }
              """)
            .header("Content-type", "application/json"))
          .andExpect(status().isCreated())
          .andExpect(MockMvcResultMatchers.content().json("{\"value\":1}"));

        then(userRegisterUseCase).should().registerUser(eq(createUserCommand));
    }

    @Test
    void registerUserWithExistingUsernameOrEmailWillReturn409() throws Exception {
        when(userRegisterUseCase.registerUser(any())).thenThrow(UserException.class);
        mockMvc.perform(post("/users")
            .content("""
                {
                "username": "test",
                "email": "test@mail.com",
                "password": "testpass",
                "sex": "MALE",
                "firstName": "testFirstName",
                "lastName": "testLastName",
                "birthday": "2000-01-01"
                }
              """)
            .header("Content-type", "application/json"))
          .andExpect(status().isConflict());
    }

    private RegisterUserCommand createUserCommand() {
        return new RegisterUserCommand(
          "test",
          "test@mail.com",
          "testpass",
          Sex.MALE,
          "testFirstName",
          "testLastName",
          LocalDate.of(2000, 1, 1));
    }
}

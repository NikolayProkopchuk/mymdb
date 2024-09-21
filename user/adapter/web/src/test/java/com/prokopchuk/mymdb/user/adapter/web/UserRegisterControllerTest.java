package com.prokopchuk.mymdb.user.adapter.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MockMvc;

import com.prokopchuk.mymdb.common.adapter.web.advice.MymdbGlobalExceptionHandler;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.adapter.web.advice.UserExceptionHandler;
import com.prokopchuk.mymdb.user.adapter.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.user.application.exception.UserNotUniqueException;
import com.prokopchuk.mymdb.user.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.user.domain.Sex;

@WebMvcTest(UserRegisterController.class)
class UserRegisterControllerTest {

    public static final String VALID_REGISTER_USER_BODY = """
        {
        "username": "test",
        "email": "test@mail.com",
        "password": "testPass",
        "sex": "MALE",
        "firstName": "testFirstName",
        "lastName": "testLastName",
        "birthday": "2000-01-01"
        }
      """;


    public static final String NOT_VALID_REGISTER_USER_BODY = """
        {
        "email": "test@mail.com",
        "password": "testPass",
        "sex": "MALE",
        "firstName": "testFirstName",
        "lastName": "testLastName",
        "birthday": "2000-01-01"
        }
      """;

    public static final RegisterUserRequestDto CREATE_USER_DTO = new RegisterUserRequestDto(
      "test",
      "test@mail.com",
      "testPass",
      Sex.MALE,
      "testFirstName",
      "testLastName",
      LocalDate.of(2000, 1, 1));

    @Configuration
    @Import({UserRegisterController.class, UserExceptionHandler.class, MymdbGlobalExceptionHandler.class})
    public static class TestConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegisterUseCase userRegisterUseCase;

    @SpyBean
    private ConversionService conversionService;

    @Test
    void registerUserTest() throws Exception {
        var createUserCommand = createUserCommand();
        UserId userId = new UserId(1L);
        doReturn(createUserCommand)
          .when(conversionService)
          .convert(CREATE_USER_DTO, RegisterUserCommand.class);
        when(userRegisterUseCase.registerUser(createUserCommand))
          .thenReturn(userId);
        mockMvc.perform(post("/users")
            .content(VALID_REGISTER_USER_BODY)
            .header("Content-type", "application/json"))
          .andExpect(status().isCreated());

        then(userRegisterUseCase).should().registerUser(eq(createUserCommand));
    }

    @Test
    void registerUserWithExistingUsernameOrEmailWillReturn409() throws Exception {
        var createUserCommand = createUserCommand();
        doReturn(createUserCommand)
          .when(conversionService)
          .convert(CREATE_USER_DTO, RegisterUserCommand.class);

        when(userRegisterUseCase.registerUser(any()))
          .thenThrow(new UserNotUniqueException(
            String.format("User with username: '%s' exists", createUserCommand.getUsername())));
        mockMvc.perform(post("/users")
            .content(VALID_REGISTER_USER_BODY)
            .header(HttpHeaders.CONTENT_TYPE, "application/json"))
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

    @Test
    void registerUserReturns400WhenRequestIsNotValid() throws Exception {
        mockMvc.perform(post("/users")
            .content(NOT_VALID_REGISTER_USER_BODY)
            .header(HttpHeaders.CONTENT_TYPE, "application/json"))
          .andExpect(status().isBadRequest());
    }
}

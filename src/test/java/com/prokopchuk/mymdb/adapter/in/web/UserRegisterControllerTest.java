package com.prokopchuk.mymdb.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.prokopchuk.mymdb.MymdbApplication;
import com.prokopchuk.mymdb.adapter.in.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.adapter.in.web.mapper.UserRequestToCommandMapper;
import com.prokopchuk.mymdb.application.UserException;
import com.prokopchuk.mymdb.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.application.port.in.command.RegisterUserCommand;
import com.prokopchuk.mymdb.config.WebSecurityConfig;
import com.prokopchuk.mymdb.domain.Sex;

@WebMvcTest(UserRegisterController.class)
@AutoConfigureMockMvc
@SpringJUnitWebConfig(classes = {MymdbApplication.class, WebSecurityConfig.class})
class UserRegisterControllerTest {

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
        String publicId = "\"abcdefghij\"";
        when(userRequestToCommandMapper.registerUserRequestToCommand(createUserDto)).thenReturn(createUserCommand);
        when(userRegisterUseCase.registerUser(createUserCommand)).thenReturn(publicId);
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
          .andExpect(MockMvcResultMatchers.content().json(publicId));

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

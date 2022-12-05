package com.prokopchuk.mymdb.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;

import com.prokopchuk.mymdb.MymdbApplication;
import com.prokopchuk.mymdb.adapter.in.web.dto.req.CreateUserRequestDto;
import com.prokopchuk.mymdb.adapter.in.web.mapper.UserRequestToCommandMapper;
import com.prokopchuk.mymdb.application.UserException;
import com.prokopchuk.mymdb.application.port.in.CreateUserCommand;
import com.prokopchuk.mymdb.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.config.WebSecurityConfig;
import com.prokopchuk.mymdb.domain.Sex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        var createUserDto = new CreateUserRequestDto("test", "test@mail.com", "testpass",
          Sex.MALE, "test", "test", LocalDate.of(1988, Month.APRIL, 10));
        var createUserCommand = createUserCommand();
        when(userRequestToCommandMapper.createUserRequestToCommand(createUserDto)).thenReturn(createUserCommand);
        when(userRegisterUseCase.registerUser(createUserCommand)).thenReturn(1L);
        mockMvc.perform(post("/users")
            .content("""
                {
                "username": "test",
                "email": "test@mail.com",
                "password": "testpass",
                "sex": "MALE",
                "firstName": "test",
                "lastName": "test",
                "birthday": "1988-04-10"
                }
              """)
            .header("Content-type", "application/json"))
          .andExpect(status().isCreated())
          .andExpect(MockMvcResultMatchers.content().json("1"));
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
                "firstName": "test",
                "lastName": "test",
                "birthday": "1988-04-10"
                }
              """)
            .header("Content-type", "application/json"))
          .andExpect(status().isConflict());
    }

    private CreateUserCommand createUserCommand() {
        return new CreateUserCommand(
          "test",
          "test@mail.com",
          "test",
          Sex.MALE,
          "test",
          "test",
          LocalDate.of(1988, Month.APRIL, 10));
    }
}

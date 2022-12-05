package com.prokopchuk.mymdb.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prokopchuk.mymdb.adapter.in.web.dto.req.CreateUserRequestDto;
import com.prokopchuk.mymdb.adapter.in.web.mapper.UserRequestToCommandMapper;
import com.prokopchuk.mymdb.application.UserException;
import com.prokopchuk.mymdb.application.port.in.UserRegisterUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRegisterController {

    private final UserRegisterUseCase registerUserUseCase;
    private final UserRequestToCommandMapper userRequestToCommandMapper;

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody CreateUserRequestDto dto) {

        var createUserCommand = userRequestToCommandMapper.createUserRequestToCommand(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
          .body(registerUserUseCase.registerUser(createUserCommand));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUserException(UserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}

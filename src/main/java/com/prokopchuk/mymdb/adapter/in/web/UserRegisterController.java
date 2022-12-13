package com.prokopchuk.mymdb.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prokopchuk.mymdb.adapter.in.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.adapter.in.web.mapper.UserRequestToCommandMapper;
import com.prokopchuk.mymdb.application.UserException;
import com.prokopchuk.mymdb.application.port.in.UserRegisterUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserRegisterController {

    private final UserRegisterUseCase registerUserUseCase;
    private final UserRequestToCommandMapper userRequestToCommandMapper;

    @PostMapping
    ResponseEntity<Long> registerUser(@RequestBody RegisterUserRequestDto dto) {
        //todo: add input validation
        var registerUserCommand = userRequestToCommandMapper.registerUserRequestToCommand(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
          .body(registerUserUseCase.registerUser(registerUserCommand));
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserException(UserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}

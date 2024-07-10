package com.prokopchuk.mymdb.user.adapter.web;

import com.prokopchuk.mymdb.common.adapter.web.annotation.WebAdapter;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.adapter.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.user.adapter.web.mapper.UserRequestToCommandMapper;
import com.prokopchuk.mymdb.user.application.exception.UserException;
import com.prokopchuk.mymdb.user.application.port.in.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserRegisterController {

    private final UserRegisterUseCase registerUserUseCase;
    private final UserRequestToCommandMapper userRequestToCommandMapper;

    @PostMapping
    ResponseEntity<UserId> registerUser(@RequestBody RegisterUserRequestDto dto) {
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

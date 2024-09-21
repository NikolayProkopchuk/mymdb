package com.prokopchuk.mymdb.user.adapter.web;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prokopchuk.mymdb.common.adapter.web.annotation.WebAdapter;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.user.adapter.web.dto.req.RegisterUserRequestDto;
import com.prokopchuk.mymdb.user.application.port.in.UserRegisterUseCase;
import com.prokopchuk.mymdb.user.application.port.in.command.RegisterUserCommand;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@WebAdapter
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserRegisterController {

    private final ConversionService conversionService;
    private final UserRegisterUseCase registerUserUseCase;

    @PostMapping
    ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequestDto dto) {
        RegisterUserCommand registerUserCommand = conversionService.convert(dto, RegisterUserCommand.class);
        UserId userId = registerUserUseCase.registerUser(registerUserCommand);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userId.getValue())
            .toUri())
          .build();
    }

}

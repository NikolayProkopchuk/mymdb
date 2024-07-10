package com.prokopchuk.mymdb.common.adapter.web.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class MymdbExceptionHandler {

    @ExceptionHandler
    ResponseEntity<String> exceptionHandler(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
    }
}

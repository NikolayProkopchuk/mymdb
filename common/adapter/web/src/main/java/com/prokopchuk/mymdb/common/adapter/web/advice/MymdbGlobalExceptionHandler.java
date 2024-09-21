package com.prokopchuk.mymdb.common.adapter.web.advice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(MymdbExceptionHandlerOrder.COMMON_EXCEPTION_HANDLER_ORDER)
public class MymdbGlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorDto unexpectedExceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto(CustomErrorCode.UNKNOWN_ERROR.getErrorCode(), List.of("Unexpected error!"));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto validationExceptionHandler(ValidationException ex) {
        log.error(ex.getMessage(), ex);
        if (ex instanceof ConstraintViolationException constraintViolationException) {
            List<String> violations = extractViolations(constraintViolationException);
            return new ErrorDto(CustomErrorCode.VALIDATION_ERROR.getErrorCode(), violations);
        }
        return new ErrorDto(CustomErrorCode.VALIDATION_ERROR.getErrorCode(), List.of(ex.getMessage()));
    }

    private List<String> extractViolations(ConstraintViolationException constraintViolationException) {
        return constraintViolationException.getConstraintViolations()
          .stream()
          .map(ConstraintViolation::getMessage)
          .collect(Collectors.toList());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        List<String> errors = ex.getBindingResult()
          .getFieldErrors()
          .stream()
          .map(error -> error.getField() + " : " + error.getDefaultMessage())
          .collect(Collectors.toList());
        return new ErrorDto(CustomErrorCode.VALIDATION_ERROR.getErrorCode(), errors);
    }
}

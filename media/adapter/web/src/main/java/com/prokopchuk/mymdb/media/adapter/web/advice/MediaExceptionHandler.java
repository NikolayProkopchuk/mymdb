package com.prokopchuk.mymdb.media.adapter.web.advice;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prokopchuk.mymdb.common.adapter.web.advice.CustomErrorCode;
import com.prokopchuk.mymdb.common.adapter.web.advice.ErrorDto;
import com.prokopchuk.mymdb.common.adapter.web.advice.MymdbExceptionHandlerOrder;
import com.prokopchuk.mymdb.media.application.service.exception.FilmNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(MymdbExceptionHandlerOrder.MEDIA_EXCEPTION_HANDLER_ORDER)
public class MediaExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDto handleFilmNotFoundException(FilmNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorDto(CustomErrorCode.FILM_NOT_FOUND.getErrorCode(), List.of(ex.getMessage()));
    }
}

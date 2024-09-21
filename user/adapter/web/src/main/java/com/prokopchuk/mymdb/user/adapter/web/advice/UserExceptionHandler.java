package com.prokopchuk.mymdb.user.adapter.web.advice;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prokopchuk.mymdb.common.adapter.web.advice.CustomErrorCode;
import com.prokopchuk.mymdb.common.adapter.web.advice.ErrorDto;
import com.prokopchuk.mymdb.common.adapter.web.advice.MymdbExceptionHandlerOrder;
import com.prokopchuk.mymdb.user.application.exception.UserNotUniqueException;

@RestControllerAdvice
@Order(MymdbExceptionHandlerOrder.USER_EXCEPTION_HANDLER_ORDER)
public class UserExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorDto handleUserNotUniqueException(UserNotUniqueException ex) {
        return new ErrorDto(CustomErrorCode.USER_NOT_UNIQUE.getErrorCode(), List.of(ex.getMessage()));
    }
}

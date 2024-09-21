package com.prokopchuk.mymdb.common.adapter.web.advice;

public enum CustomErrorCode implements ErrorCode {

    UNKNOWN_ERROR,
    VALIDATION_ERROR,
    FILM_NOT_FOUND,
    USER_NOT_UNIQUE;

    @Override
    public String getErrorCode() {
        return name();
    }
}

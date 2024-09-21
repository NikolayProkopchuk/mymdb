package com.prokopchuk.mymdb.media.application.service.exception;

import com.prokopchuk.mymdb.common.domain.DomainException;

public class FilmNotFoundException extends DomainException {
    public FilmNotFoundException(String message) {
        super(message);
    }
}

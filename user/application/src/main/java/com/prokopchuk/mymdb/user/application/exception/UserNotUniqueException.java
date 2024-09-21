package com.prokopchuk.mymdb.user.application.exception;

public class UserNotUniqueException extends RuntimeException {
    public UserNotUniqueException(String message) {
        super(message);
    }
}

package com.prokopchuk.mymdb.user.application.port.out;

public interface PasswordHashingPort {

    String hash(String rawPassword);
}

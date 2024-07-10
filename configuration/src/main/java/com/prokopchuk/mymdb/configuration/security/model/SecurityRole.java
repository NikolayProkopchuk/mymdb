package com.prokopchuk.mymdb.configuration.security.model;

import org.springframework.security.core.GrantedAuthority;

public enum SecurityRole implements GrantedAuthority {
    ROLE_USER, ROLE_SUPER_ADMIN, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

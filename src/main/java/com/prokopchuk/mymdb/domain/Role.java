package com.prokopchuk.mymdb.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_SUPER_ADMIN, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

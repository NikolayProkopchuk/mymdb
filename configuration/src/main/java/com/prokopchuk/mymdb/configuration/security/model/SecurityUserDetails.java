package com.prokopchuk.mymdb.configuration.security.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityUserDetails implements UserDetails {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private LocalDate birthday;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder.Default
    private Set<SecurityRole> authorities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

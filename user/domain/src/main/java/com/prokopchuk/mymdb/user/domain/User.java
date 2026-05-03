package com.prokopchuk.mymdb.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.prokopchuk.mymdb.common.domain.entity.AggregateRoot;
import com.prokopchuk.mymdb.common.domain.value.UserId;


public class User extends AggregateRoot<UserId> {

    private String username;
    private String email;
    private String password;
    private Sex sex;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private Set<Role> roles = new HashSet<>();

    private User(Builder builder) {
        setId(builder.id);
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.sex = builder.sex;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthday = builder.birthday;
        this.accountNonExpired = builder.accountNonExpired;
        this.accountNonLocked = builder.accountNonLocked;
        this.credentialsNonExpired = builder.credentialsNonExpired;
        this.enabled = builder.enabled;
        this.roles = builder.roles != null ? new HashSet<>(builder.roles) : new HashSet<>();
        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
        this.updatedAt = builder.updatedAt;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void initializeOnRegistration(String hashedPassword) {
        password = hashedPassword;
        roles.add(Role.ROLE_USER);
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Sex getSex() {
        return sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UserId id;
        private String username;
        private String email;
        private String password;
        private Sex sex;
        private String firstName;
        private String lastName;
        private LocalDate birthday;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;
        private Set<Role> roles = new HashSet<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private Builder() {
        }

        public Builder id(UserId val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder sex(Sex val) {
            sex = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder birthday(LocalDate val) {
            birthday = val;
            return this;
        }

        public Builder accountNonExpired(boolean val) {
            accountNonExpired = val;
            return this;
        }

        public Builder accountNonLocked(boolean val) {
            accountNonLocked = val;
            return this;
        }

        public Builder credentialsNonExpired(boolean val) {
            credentialsNonExpired = val;
            return this;
        }

        public Builder enabled(boolean val) {
            enabled = val;
            return this;
        }

        public Builder roles(Set<Role> val) {
            roles = val;
            return this;
        }

        public Builder createdAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDateTime val) {
            updatedAt = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

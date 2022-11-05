package com.prokopchuk.mymdb.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.Hibernate;

import com.prokopchuk.mymdb.domain.Role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserRoleEntity {

    @EmbeddedId
    private UserRoleId userRoleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private Role role;

    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        UserRoleEntity userRole = (UserRoleEntity) obj;
        return userRoleId != null && Objects.equals(userRoleId, userRole.userRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleId);
    }
}

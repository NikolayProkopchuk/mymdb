package com.prokopchuk.mymdb.common.persistence.entity;

import java.util.Set;

import com.prokopchuk.mymdb.user.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(schema = "users", name = "roles")
@Getter
@Setter
public class RoleEntity {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Role name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
}

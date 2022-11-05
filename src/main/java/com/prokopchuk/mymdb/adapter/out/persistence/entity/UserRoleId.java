package com.prokopchuk.mymdb.adapter.out.persistence.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.prokopchuk.mymdb.domain.Role;

import lombok.Data;

@Embeddable
@Data
public class UserRoleId implements Serializable {

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Role role;
}

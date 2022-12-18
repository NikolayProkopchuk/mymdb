package com.prokopchuk.mymdb.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.RoleEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.UserRepo;
import com.prokopchuk.mymdb.domain.Role;
import com.prokopchuk.mymdb.domain.Sex;
import com.prokopchuk.mymdb.domain.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserPersistenceAdapter.class)
@Testcontainers
@ActiveProfiles("test-flyway")
class UserPersistenceAdapterTest {

    @Autowired
    private UserPersistenceAdapter userPersistenceAdapter;

    @Autowired
    private UserRepo userRepo;

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    void loadUserByName() {
        var user = userPersistenceAdapter.loadUserByUsername("test").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getEmail()).isEqualTo("test@mail.com");
        assertThat(user.getRoles()).contains(Role.ROLE_USER);
    }

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    void loadUserByEmail() {
        var user = userPersistenceAdapter.loadUserByUsername("test").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getEmail()).isEqualTo("test@mail.com");
        assertThat(user.getRoles()).contains(Role.ROLE_USER);
    }

    @Test
    void registerUser() {
        var user = User.builder()
          .username("test")
          .email("test@mail.com")
          .publicId("abcdefghij")
          .password("testPass")
          .sex(Sex.MALE)
          .firstName("testFirstName")
          .lastName("testLastName")
          .birthday(LocalDate.of(2000, 1, 1))
          .roles(Set.of(Role.ROLE_USER))
          .accountNonExpired(true)
          .accountNonLocked(true)
          .credentialsNonExpired(true)
          .enabled(true)
          .build();

        userPersistenceAdapter.registerUser(user);

        var savedUserEntity = userRepo.findUserEntityByUsername("test").orElseThrow();
        assertThat(savedUserEntity.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUserEntity.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUserEntity.getPublicId()).isEqualTo("abcdefghij");
        assertThat(savedUserEntity.getSex()).isEqualTo(user.getSex());
        assertThat(savedUserEntity.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(savedUserEntity.getLastName()).isEqualTo(user.getLastName());
        assertThat(savedUserEntity.getBirthday()).isEqualTo(user.getBirthday());
        assertThat(savedUserEntity.getRoles()).hasSize(user.getRoles().size());
        assertThat(savedUserEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()))
          .isEqualTo(user.getRoles());
    }
}

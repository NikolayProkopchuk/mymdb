package com.prokopchuk.mymdb.config;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.RoleRepo;
import com.prokopchuk.mymdb.application.service.UserService;
import com.prokopchuk.mymdb.domain.Sex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Bootstrap {

    private final UserService userService;

    private final RoleRepo roleRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void addStartUsersIfTheyNotExist() {
        var superAdminRole = roleRepo.findByName("ROLE_SUPER_ADMIN").orElseThrow();
        var adminRole = roleRepo.findByName("ROLE_ADMIN").orElseThrow();
        var userRole = roleRepo.findByName("ROLE_USER").orElseThrow();
        try {
            userService.loadUserByUsername("superAdmin");
        } catch (UsernameNotFoundException ex) {
            log.info("supper admin user was not found and will be created with username: supperAdmin");
            var superAdmin = UserEntity.builder()
              .username("superAdmin")
              .email("supreme@mail.com")
              .password("supperAdminStrongPassword")
              .sex(Sex.MALE)
              .firstName("Super")
              .lastName("Admin")
              .roles(Set.of(superAdminRole, adminRole, userRole))
              .birthday(LocalDate.of(1988, Month.APRIL, 10))
              .enabled(true)
              .accountNonLocked(true)
              .accountNonExpired(true)
              .credentialsNonExpired(true)
              .build();

            userService.saveUser(superAdmin);
        }

        try {
            userService.loadUserByUsername("admin");
        } catch (UsernameNotFoundException ex) {
            log.info("admin user was not found and will be created with username: admin");
            var admin = UserEntity.builder()
              .username("admin")
              .email("admin@mail.com")
              .password("adminStrongPassword")
              .sex(Sex.MALE)
              .firstName("Ordinary")
              .lastName("Admin")
              .roles(Set.of(adminRole, userRole))
              .birthday(LocalDate.of(1994, Month.SEPTEMBER, 4))
              .enabled(true)
              .accountNonLocked(true)
              .accountNonExpired(true)
              .credentialsNonExpired(true)
              .build();

            userService.saveUser(admin);
        }

        try {
            userService.loadUserByUsername("testUser");
        } catch (UsernameNotFoundException ex) {
            log.info("user was not found and will be created with username: testUser");
            var user = UserEntity.builder()
              .username("testUser")
              .email("testUser@mail.com")
              .password("testUserStrongPassword")
              .sex(Sex.FEMALE)
              .firstName("test")
              .lastName("user")
              .roles(Set.of(userRole))
              .birthday(LocalDate.of(2000, Month.DECEMBER, 1))
              .enabled(true)
              .accountNonLocked(true)
              .accountNonExpired(true)
              .credentialsNonExpired(true)
              .build();
            userService.saveUser(user);
        }
    }
}

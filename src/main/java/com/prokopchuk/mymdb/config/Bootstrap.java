package com.prokopchuk.mymdb.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.prokopchuk.mymdb.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.domain.Role;
import com.prokopchuk.mymdb.domain.Sex;
import com.prokopchuk.mymdb.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Bootstrap {

    private final RegisterUserPort registerUserPort;
    private final UserDetailsService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void addStartUsersIfTheyNotExist() {
        try {
            userService.loadUserByUsername("superAdmin");
        } catch (UsernameNotFoundException ex) {
            log.info("supper admin user was not found and will be created with username: supperAdmin");
            var superAdmin = User.builder()
              .username("superAdmin")
              .email("supreme@mail.com")
              .password(passwordEncoder.encode("supperAdminStrongPassword"))
              .publicId(RandomStringUtils.randomAlphabetic(10))
              .sex(Sex.MALE)
              .firstName("Super")
              .lastName("Admin")
              .birthday(LocalDate.of(1988, Month.APRIL, 10))
              .enabled(true)
              .accountNonLocked(true)
              .accountNonExpired(true)
              .credentialsNonExpired(true)
              .createdAt(LocalDateTime.now())
              .build();

            superAdmin.addRole(Role.ROLE_USER);
            superAdmin.addRole(Role.ROLE_ADMIN);
            superAdmin.addRole(Role.ROLE_SUPER_ADMIN);

            registerUserPort.registerUser(superAdmin);
        }

        try {
            userService.loadUserByUsername("admin");
        } catch (UsernameNotFoundException ex) {
            log.info("admin user was not found and will be created with username: admin");
            var admin = User.builder()
              .username("admin")
              .email("admin@mail.com")
              .publicId(RandomStringUtils.randomAlphabetic(10))
              .password(passwordEncoder.encode("adminStrongPassword"))
              .sex(Sex.MALE)
              .firstName("Ordinary")
              .lastName("Admin")
              .birthday(LocalDate.of(1994, Month.SEPTEMBER, 4))
              .enabled(true)
              .accountNonLocked(true)
              .accountNonExpired(true)
              .credentialsNonExpired(true)
              .createdAt(LocalDateTime.now())
              .build();

            admin.addRole(Role.ROLE_USER);
            admin.addRole(Role.ROLE_ADMIN);

            registerUserPort.registerUser(admin);
        }

        try {
            userService.loadUserByUsername("testUser");
        } catch (UsernameNotFoundException ex) {
            log.info("user was not found and will be created with username: testUser");
            var user = User.builder()
              .username("testUser")
              .email("testUser@mail.com")
              .publicId(RandomStringUtils.randomAlphabetic(10))
              .password(passwordEncoder.encode("testUserStrongPassword"))
              .sex(Sex.FEMALE)
              .firstName("test")
              .lastName("user")
              .birthday(LocalDate.of(2000, Month.DECEMBER, 1))
              .enabled(true)
              .accountNonLocked(true)
              .accountNonExpired(true)
              .credentialsNonExpired(true)
              .createdAt(LocalDateTime.now())
              .build();

            user.addRole(Role.ROLE_USER);
            registerUserPort.registerUser(user);
        }
    }
}

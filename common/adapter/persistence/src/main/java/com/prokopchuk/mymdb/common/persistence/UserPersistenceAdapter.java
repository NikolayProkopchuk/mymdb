package com.prokopchuk.mymdb.common.persistence;

import java.util.Objects;
import java.util.Optional;

import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;

import com.prokopchuk.mymdb.common.domain.value.UserId;
import com.prokopchuk.mymdb.common.persistence.annotation.PersistenceAdapter;
import com.prokopchuk.mymdb.common.persistence.entity.UserEntity;
import com.prokopchuk.mymdb.common.persistence.repo.RoleRepo;
import com.prokopchuk.mymdb.common.persistence.repo.UserRepo;
import com.prokopchuk.mymdb.user.application.port.out.LoadUserPort;
import com.prokopchuk.mymdb.user.application.port.out.RegisterUserPort;
import com.prokopchuk.mymdb.user.domain.User;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, RegisterUserPort {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;
    private final ConversionService conversionService;

    @Override
    public Optional<User> loadUserByUsername(String username) {
        return userRepo.findUserEntityByUsername(username)
          .map(userEntity -> conversionService.convert(userEntity, User.class));
    }

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return userRepo.findUserEntityByEmail(email)
          .map(userEntity -> conversionService.convert(userEntity, User.class));
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        var userEntity = conversionService.convert(user, UserEntity.class);
        Objects.requireNonNull(userEntity);
        for (var role : user.getRoles()) {
            var roleEntity = roleRepo.findByName(role)
                    .orElseThrow();
            userEntity.addRole(roleEntity);
        }
        userRepo.save(userEntity);
        user.setId(new UserId(userEntity.getId()));
        return user;
    }
}

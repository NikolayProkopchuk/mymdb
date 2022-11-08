package com.prokopchuk.mymdb.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserEntityByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found by username : %s", username)));
    }

    public UserEntity saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepo.save(userEntity);
    }
}

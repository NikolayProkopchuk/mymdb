package com.prokopchuk.mymdb;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.prokopchuk.mymdb.adapter.in.web.mapper.CreateFilmRequestToCommandMapper;
import com.prokopchuk.mymdb.adapter.in.web.mapper.UserRequestToCommandMapper;
import com.prokopchuk.mymdb.adapter.out.persistence.mapper.FilmFilmEntityMapper;
import com.prokopchuk.mymdb.adapter.out.persistence.mapper.UserUserEntityMapper;
import com.prokopchuk.mymdb.application.service.mapper.CreateFilmCommandToFilmMapper;
import com.prokopchuk.mymdb.application.service.mapper.UserCommandUserMapper;

@SpringBootApplication
public class MymdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymdbApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserRequestToCommandMapper userRequestToCommandMapper() {
        return Mappers.getMapper(UserRequestToCommandMapper.class);
    }

    @Bean
    public UserUserEntityMapper userUserEntityMapper() {
        return Mappers.getMapper(UserUserEntityMapper.class);
    }

    @Bean
    public UserCommandUserMapper userCommandUserMapper() {
        return Mappers.getMapper(UserCommandUserMapper.class);
    }

    @Bean
    public CreateFilmRequestToCommandMapper createFilmRequestToCommandMapper() {
        return Mappers.getMapper(CreateFilmRequestToCommandMapper.class);
    }

    @Bean
    public CreateFilmCommandToFilmMapper createFilmCommandToFilmMapper() {
        return Mappers.getMapper(CreateFilmCommandToFilmMapper.class);
    }

    @Bean
    public FilmFilmEntityMapper filmFilmEntityMapper() {
        return Mappers.getMapper(FilmFilmEntityMapper.class);
    }

}

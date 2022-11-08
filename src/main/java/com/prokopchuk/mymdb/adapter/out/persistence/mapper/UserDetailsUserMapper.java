package com.prokopchuk.mymdb.adapter.out.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;

import com.prokopchuk.mymdb.domain.User;

@Mapper
public interface UserDetailsUserMapper {

    UserDetailsUserMapper INSTANCE = Mappers.getMapper(UserDetailsUserMapper.class);

    User userDetailsToUser(UserDetails userDetails);
}

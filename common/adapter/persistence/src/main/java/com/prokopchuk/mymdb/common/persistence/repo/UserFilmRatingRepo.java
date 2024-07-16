package com.prokopchuk.mymdb.common.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.common.persistence.entity.UserFilmRatingEntity;
import com.prokopchuk.mymdb.common.persistence.entity.UserFilmRatingId;


public interface UserFilmRatingRepo extends JpaRepository<UserFilmRatingEntity, UserFilmRatingId> {
}

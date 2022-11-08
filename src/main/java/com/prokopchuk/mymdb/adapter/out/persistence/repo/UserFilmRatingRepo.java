package com.prokopchuk.mymdb.adapter.out.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserFilmRatingEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserFilmRatingId;

public interface UserFilmRatingRepo extends JpaRepository<UserFilmRatingEntity, UserFilmRatingId> {
}

package com.prokopchuk.mymdb.common.persistence.repo;

import com.prokopchuk.mymdb.common.persistence.entity.UserFilmRatingEntity;
import com.prokopchuk.mymdb.common.persistence.entity.UserFilmRatingId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserFilmRatingRepo extends JpaRepository<UserFilmRatingEntity, UserFilmRatingId> {
}

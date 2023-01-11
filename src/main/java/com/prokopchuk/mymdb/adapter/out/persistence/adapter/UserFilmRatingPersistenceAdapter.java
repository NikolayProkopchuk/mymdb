package com.prokopchuk.mymdb.adapter.out.persistence.adapter;

import java.time.LocalDateTime;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserFilmRatingEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.entity.UserFilmRatingId;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.FilmRepo;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.UserFilmRatingRepo;
import com.prokopchuk.mymdb.adapter.out.persistence.repo.UserRepo;
import com.prokopchuk.mymdb.application.port.out.RateFilmPort;
import com.prokopchuk.mymdb.common.annotation.PersistenceAdapter;
import com.prokopchuk.mymdb.domain.UserRating;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserFilmRatingPersistenceAdapter implements RateFilmPort {

    private final UserRepo userRepo;
    private final FilmRepo filmRepo;

    private final UserFilmRatingRepo userFilmRatingRepo;

    @Override
    public UserRating rate(UserRating userRating) {
        UserFilmRatingId userFilmRatingId = new UserFilmRatingId(userRating.user().getId(), userRating.film().getId());
        var userFilmRatingEntityOptional = userFilmRatingRepo.findById(userFilmRatingId);

        userFilmRatingEntityOptional.ifPresentOrElse(
          entity -> updateEntity(entity, userRating),
          () -> createEntity(userRating));

        return userRating;
    }

    private void updateEntity(UserFilmRatingEntity entity, UserRating userRating) {
        entity.setRating(userRating.rating());
        entity.setUpdatedAt(LocalDateTime.now());
    }

    private void createEntity(UserRating userRating) {
        var userEntity = userRepo.getReferenceById(userRating.user().getId());
        var filmEntity = filmRepo.getReferenceById(userRating.film().getId());

        UserFilmRatingEntity ratingEntity = new UserFilmRatingEntity();
        ratingEntity.setUser(userEntity);
        ratingEntity.setFilm(filmEntity);
        ratingEntity.setRating(userRating.rating());

        userFilmRatingRepo.save(ratingEntity);
    }
}

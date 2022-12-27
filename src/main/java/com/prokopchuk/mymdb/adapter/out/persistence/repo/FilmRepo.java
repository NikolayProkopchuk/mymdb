package com.prokopchuk.mymdb.adapter.out.persistence.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntity;
import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntityDto;


public interface FilmRepo extends PagingAndSortingRepository<FilmEntity, Long>, JpaRepository<FilmEntity, Long> {

    @Query("""
          select new com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntityDto(
          f.id,
          f.name,
          f.description,
          f.productionDate,
          avg(r.rating))
      from FilmEntity f
      left join UserFilmRatingEntity r on r.film = f
      group by f
          """)
    Page<FilmEntityDto> findFilmsDto(Pageable pageable);

    @Query("""
          select new com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntityDto(
          f.id,
          f.name,
          f.description,
          f.productionDate,
          avg(r.rating))
      from FilmEntity f
      left join UserFilmRatingEntity r on r.film = f
      where f.id = :id
      group by f
          """)
    FilmEntityDto findFilmDto(@Param("id") Long id);
}

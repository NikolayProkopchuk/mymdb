package com.prokopchuk.mymdb.adapter.out.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prokopchuk.mymdb.adapter.out.persistence.entity.FilmEntity;

public interface FilmRepo extends JpaRepository<FilmEntity, Long> {
}

package com.prokopchuk.mymdb.common.persistence.mapper;

import org.mapstruct.Mapper;

import com.prokopchuk.mymdb.common.domain.value.BaseId;
import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;

@Mapper
public interface BaseIdToLongMapper {
    default Long baseIdToLong(BaseId<Long> baseLongId) {
        return baseLongId != null
                ? baseLongId.getValue()
                : null;
    }

    default UserId longToUserId(Long id) {
        return new UserId(id);
    }

    default FilmId longToFilmId(Long id) {
        return new FilmId(id);
    }
}

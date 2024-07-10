package com.prokopchuk.mymdb.common.persistence.mapper;

import com.prokopchuk.mymdb.common.domain.value.BaseId;
import com.prokopchuk.mymdb.common.domain.value.FilmId;
import com.prokopchuk.mymdb.common.domain.value.UserId;
import org.mapstruct.Mapper;

@Mapper
public interface BaseIdToLongMapper {
    default Long baseIdToLong(BaseId<Long> baseLongId) {
        return baseLongId != null
                ? baseLongId.getValue()
                : null;
    }

    default UserId LongToUserId(Long id) {
        return new UserId(id);
    }

    default FilmId LongToFilmId(Long id) {
        return new FilmId(id);
    }
}

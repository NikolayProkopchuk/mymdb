package com.prokopchuk.mymdb.adapter.out.persistence.entity;

import com.prokopchuk.mymdb.domain.Rating;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RatingConverter implements AttributeConverter<Rating, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Rating attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(Integer dbData) {
        return Rating.getInstance(dbData);
    }
}

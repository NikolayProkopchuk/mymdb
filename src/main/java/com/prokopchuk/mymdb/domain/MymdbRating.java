package com.prokopchuk.mymdb.domain;

import lombok.Data;

@Data
public class MymdbRating {

    private int votersCount;

    private double value;

    private Film film;

}

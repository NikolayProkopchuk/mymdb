package com.prokopchuk.mymdb.domain;

public record UserRating(User user, Film film, double rating) {
}

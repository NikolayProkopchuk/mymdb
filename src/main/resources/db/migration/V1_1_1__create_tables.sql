CREATE TABLE users
(
    id         BIGINT PRIMARY KEY,
    username   TEXT      NOT NULL UNIQUE,
    email      TEXT      NOT NULL UNIQUE,
    password   TEXT      NOT NULL,
    sex        TEXT      NOT NULL,
    first_name TEXT      NOT NULL,
    last_name  TEXT      NOT NULL,
    birthday   DATE      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE films
(
    id              BIGINT PRIMARY KEY,
    name            TEXT      NOT NULL,
    description     TEXT      NOT NULL,
    production_date DATE      NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP
);

CREATE TABLE users_films_rating
(
    user_id bigint,
    film_id bigint,
    rating  int,

    CONSTRAINT users_users_films_rating_FK FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT films_users_films_rating_FK FOREIGN KEY (film_id) REFERENCES films (id),
    PRIMARY KEY (user_id, film_id)
);

CREATE TABLE users_roles
(
    user_id    BIGINT,
    role       TEXT,
    created_at TIMESTAMP,
    CONSTRAINT users_roles_users_FK FOREIGN KEY (user_id) REFERENCES users (id),
    PRIMARY KEY (user_id, role)
);

CREATE SEQUENCE users_seq START WITH 1;

CREATE SEQUENCE films_seq START WITH 1;

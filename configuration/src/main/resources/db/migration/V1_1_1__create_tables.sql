CREATE SCHEMA IF NOT EXISTS users;

CREATE TABLE IF NOT EXISTS users.users
(
    id                      BIGSERIAL
        CONSTRAINT pk_users_users_id PRIMARY KEY,
    username                TEXT      NOT NULL UNIQUE,
    email                   TEXT      NOT NULL UNIQUE,
    password                TEXT      NOT NULL,
    sex                     TEXT      NOT NULL,
    first_name              TEXT      NOT NULL,
    last_name               TEXT      NOT NULL,
    birthday                DATE      NOT NULL,
    account_non_expired     BOOLEAN   NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN   NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN   NOT NULL DEFAULT TRUE,
    enabled                 BOOLEAN   NOT NULL DEFAULT TRUE,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE users.roles
(
    id   BIGSERIAL
        CONSTRAINT pk_users_roles_id PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE users.users_roles
(
    user_id    BIGINT,
    role_id    BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_users_users_roles_user_id FOREIGN KEY (user_id) REFERENCES users.users (id),
    CONSTRAINT fk_users_users_roles_role_id FOREIGN KEY (role_id) REFERENCES users.roles (id),
    CONSTRAINT pk_users_users_roles PRIMARY KEY (user_id, role_id)
);

CREATE SCHEMA IF NOT EXISTS media;

CREATE TABLE IF NOT EXISTS media.films
(
    id              BIGSERIAL
        CONSTRAINT pk_media_films_id PRIMARY KEY,
    name            TEXT      NOT NULL,
    description     TEXT      NOT NULL,
    production_date DATE      NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS media.users_films_rating
(
    user_id    BIGINT,
    film_id    BIGINT,
    rating     int,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_media_users_films_rating_user_id FOREIGN KEY (user_id) REFERENCES users.users (id),
    CONSTRAINT fk_media_users_films_rating_film_id FOREIGN KEY (film_id) REFERENCES media.films (id),
    CONSTRAINT pk_media_users_films_rating PRIMARY KEY (user_id, film_id)
);

INSERT INTO users.roles (name)
VALUES ('ROLE_SUPER_ADMIN'),
       ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO media.films (name, description, production_date)
VALUES ('Shawshenk redemption', 'Movie about convicted by mistake man and his prison break',
        '1994-09-10'),
       ('The green mile',
        'Movie about death row corrections officer and convicted black man after his imprisoning strange supernatural effects started happening',
        '1999-12-06'),
       ('Batman Begins',
        'The story tells how son of billionaire Bruce Wayne became a Batman after tragic death of his parens and started to fight against criminals and corruption in his city Gotham',
        '2005-06-06');

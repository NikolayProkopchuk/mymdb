alter table films
    alter column created_at drop not null;

alter table users
    alter column created_at drop not null;

alter table users_films_rating
    alter column created_at drop not null;

alter table users_roles
    alter column created_at drop not null;
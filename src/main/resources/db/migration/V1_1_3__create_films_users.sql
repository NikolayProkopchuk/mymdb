ALTER TABLE users
    ADD COLUMN account_non_expired     BOOLEAN,
    ADD COLUMN account_non_locked      BOOLEAN,
    ADD COLUMN credentials_non_expired BOOLEAN,
    ADD COLUMN enabled                 BOOLEAN;

CREATE TABLE roles
(
    id INT PRIMARY KEY,
    name TEXT not null unique
);

CREATE SEQUENCE roles_seq start 1;

ALTER TABLE users_roles
    DROP COLUMN role,
    ADD COLUMN role_id INT,
    ADD CONSTRAINT users_roles_roles_FK FOREIGN KEY (role_id) REFERENCES roles (id);

insert into roles (id, name)
values (nextval('roles_seq'), 'ROLE_SUPER_ADMIN'),
       (nextval('roles_seq'), 'ROLE_ADMIN'),
       (nextval('roles_seq'), 'ROLE_USER');

insert into films (id, name, description, production_date)
VALUES (nextval('films_seq'), 'Shawshenk redemption', 'Movie about convicted by mistake man and his prison break',
        '1994-09-10'),
       (nextval('films_seq'), 'The green mile',
        'Movie about death row corrections officer and convicted black man after his imprisoning strange supernatural effects started happening',
        '1999-12-06'),
       (nextval('films_seq'), 'Batman Begins',
        'The story tells how son of billionaire Bruce Wayne became a Batman after tragic death of his parens and started to fight against criminals and corruption in his city Gotham',
        '2005-06-06');

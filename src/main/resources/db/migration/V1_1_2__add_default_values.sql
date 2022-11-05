ALTER TABLE users
    ALTER COLUMN created_at SET DEFAULT now();

ALTER TABLE films
    ALTER COLUMN created_at SET DEFAULT now();

ALTER TABLE users_films_rating
    ADD COLUMN created_at TIMESTAMP DEFAULT now();

ALTER TABLE users_films_rating
    ADD COLUMN updated_at TIMESTAMP;

ALTER TABLE users_roles
    ALTER COLUMN created_at set DEFAULT now();

DELETE FROM users_roles;
DELETE FROM users;

ALTER TABLE films ALTER created_at SET NOT NULL;
ALTER TABLE users ALTER created_at SET NOT NULL;
ALTER TABLE users_films_rating ALTER created_at SET NOT NULL;
ALTER TABLE users_roles ALTER created_at SET NOT NULL;

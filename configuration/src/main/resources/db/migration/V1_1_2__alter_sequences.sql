--------------------------------------------------
-- Alter sequence increments to perform batches --
--------------------------------------------------

ALTER SEQUENCE media.films_id_seq INCREMENT BY 10;
ALTER SEQUENCE users.users_id_seq INCREMENT BY 10;
ALTER SEQUENCE users.roles_id_seq INCREMENT BY 10;

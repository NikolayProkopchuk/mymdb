INSERT INTO users (id, username, email, password, sex, first_name, last_name, birthday,
                   account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES (nextval('users_seq'), 'test', 'test@mail.com', 'testPass', 'MALE', 'testFirstName', 'testLastName', '2000-01-01', true, true, true, true);
INSERT INTO users_roles (user_id, role_id) VALUES (currval('users_seq'), (select id from roles where name = 'ROLE_USER'));

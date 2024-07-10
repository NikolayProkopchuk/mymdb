INSERT INTO users.users (username, email, password, sex, first_name, last_name, birthday,
                   account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('test', 'test@mail.com', 'testPass', 'MALE', 'testFirstName', 'testLastName', '2000-01-01', true, true, true, true);
INSERT INTO users.users_roles (user_id, role_id)
VALUES ((select id from users.users where username = 'test'), (select id from users.roles where name = 'ROLE_USER'));

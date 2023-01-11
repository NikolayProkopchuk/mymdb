INSERT INTO users (id, username, email, password, sex, first_name, last_name, birthday,
                   account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES (100, 'test', 'test@mail.com', 'testPass', 'MALE', 'testFirstName', 'testLastName', '2000-01-01', true, true,
        true, true);
INSERT INTO films (id, name, description, production_date)
VALUES (200, 'test name', 'test description', '2000-01-01');
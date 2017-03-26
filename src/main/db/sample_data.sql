TRUNCATE TABLE feedback.emails CASCADE;

INSERT INTO feedback.emails VALUES (1, 'kazimieras.senvaitis@gmail.com');
INSERT INTO feedback.emails VALUES (2, 'lt.marys@gmail.com');
INSERT INTO feedback.emails VALUES (3, 'viktorja.vs@gmail.com');
INSERT INTO feedback.emails VALUES (4, 'justinasbukas@gmail.com');
INSERT INTO feedback.emails VALUES (5, 'mikelionislukas@yahoo.com');


TRUNCATE TABLE feedback.users CASCADE;

INSERT INTO feedback.users VALUES (1, 1, 'Kazimieras', 'Senvaitis', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (2, 2, 'Marius', 'Sukarevičius', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (3, 3, 'Viktorija', 'Sujetaitė', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (4, 4, 'Justinas', 'Bukas', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (5, 5, 'Lukas', 'Mikelionis', 'testpassword', true, false);
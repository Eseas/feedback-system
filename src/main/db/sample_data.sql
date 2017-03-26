TRUNCATE TABLE feedback.potential_users CASCADE;
ALTER SEQUENCE feedback.potential_users_id_seq RESTART WITH 1;

INSERT INTO feedback.potential_users VALUES (DEFAULT, 'algirdas.simanauskas@gmail.com');
INSERT INTO feedback.potential_users VALUES (DEFAULT, 'audrius.tvarijonas@gmail.com');
INSERT INTO feedback.potential_users VALUES (DEFAULT, 'marius.alchimavicius@gmail.com');
INSERT INTO feedback.potential_users VALUES (DEFAULT, 'jonas.brusokas@gmail.com');


TRUNCATE TABLE feedback.users CASCADE;
ALTER SEQUENCE feedback.users_id_seq RESTART WITH 1;

INSERT INTO feedback.users VALUES (DEFAULT, 'kazimieras.senvaitis@gmail.com', 'Kazimieras', 'Senvaitis', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (DEFAULT, 'lt.marys@gmail.com', 'Marius', 'Sukarevičius', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (DEFAULT, 'viktorja.vs@gmail.com', 'Viktorija', 'Sujetaitė', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (DEFAULT, 'justinasbukas@gmail.com', 'Justinas', 'Bukas', 'testpassword', true, false);
INSERT INTO feedback.users VALUES (DEFAULT, 'mikelionislukas@yahoo.com', 'Lukas', 'Mikelionis', 'testpassword', true, false);


TRUNCATE TABLE feedback.surveys CASCADE;
ALTER SEQUENCE feedback.surveys_id_seq RESTART WITH 1;

INSERT INTO feedback.surveys VALUES (DEFAULT, 1, true, 'Patogumas dirbti', 'Prašome atsakyti į klausimus');
INSERT INTO feedback.surveys VALUES (DEFAULT, 2, true,'Atlyginimas', 'Prašome atsakyti į klausimus');
INSERT INTO feedback.surveys VALUES (DEFAULT, 1, true,'Socialinės garantijos', 'Prašome atsakyti į klausimus');
INSERT INTO feedback.surveys VALUES (DEFAULT, 3, true,'Laisvalaikis', 'Prašome atsakyti į klausimus');
INSERT INTO feedback.surveys VALUES (DEFAULT, 4, true,'Pramogos', 'Prašome atsakyti į klausimus');

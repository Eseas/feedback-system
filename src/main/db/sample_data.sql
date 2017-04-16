INSERT INTO feedback.potential_users VALUES (DEFAULT, 'harry.ryan@gmail.com');
INSERT INTO feedback.potential_users VALUES (DEFAULT, 'alfie.byrne@gmail.com');
INSERT INTO feedback.potential_users VALUES (DEFAULT, 'kian.watts@gmail.com');

INSERT INTO feedback.users VALUES (DEFAULT, 'test', 'TestName', 'TestLastName', 'test', true, false, 1);
INSERT INTO feedback.users VALUES (DEFAULT, 'tyler.gallagher@gmail.com', 'Tyler', 'Gallagher', 'testpassword', true, false, 1);
INSERT INTO feedback.users VALUES (DEFAULT, 'bradley.bell@gmail.com', 'Bradley', 'Bell', 'testpassword', true, false, 1);
INSERT INTO feedback.users VALUES (DEFAULT, 'conner.russo@gmail.com', 'Conner', 'Russo', 'testpassword', true, false, 1);

INSERT INTO feedback.surveys VALUES (DEFAULT, 1, FALSE, 'Workplace Confort', 'Please answer a few questions');
INSERT INTO feedback.surveys VALUES (DEFAULT, 1, TRUE,'Salary', 'We kindly ask ypu to answer some questions');
INSERT INTO feedback.surveys VALUES (DEFAULT, 2, TRUE,'Social Security', 'This survey is anonymous');

INSERT INTO feedback.slider_questions VALUES (DEFAULT, 1, TRUE, 1, 6, 1, 'Assess office environment');
INSERT INTO feedback.slider_questions VALUES (DEFAULT, 2, FALSE, 1, 10, 1, 'Do you like your chair?');
INSERT INTO feedback.slider_questions VALUES (DEFAULT, 3, TRUE, 1, 8, 1, 'Do you need a new monitor?');

INSERT INTO feedback.text_questions VALUES (DEFAULT, 1, TRUE, 'Assess office environment');
INSERT INTO feedback.text_questions VALUES (DEFAULT, 2, FALSE, 'Do you like your chair?');
INSERT INTO feedback.text_questions VALUES (DEFAULT, 3, TRUE, 'Do you need a new monitor?');

INSERT INTO feedback.option_questions VALUES (DEFAULT, 1, TRUE, TRUE, 'Assess office environment');
INSERT INTO feedback.option_questions VALUES (DEFAULT, 2, FALSE, TRUE, 'Do you like your chair?');
INSERT INTO feedback.option_questions VALUES (DEFAULT, 3, TRUE, FALSE,'Do you need a new monitor?');

INSERT INTO feedback.option_values VALUES (DEFAULT, 1, 'Great');
INSERT INTO feedback.option_values VALUES (DEFAULT, 1, 'Medium');
INSERT INTO feedback.option_values VALUES (DEFAULT, 1, 'Bad');
INSERT INTO feedback.option_values VALUES (DEFAULT, 2, 'Yes');
INSERT INTO feedback.option_values VALUES (DEFAULT, 2, 'No');
INSERT INTO feedback.option_values VALUES (DEFAULT, 3, 'ASAP');
INSERT INTO feedback.option_values VALUES (DEFAULT, 3, 'No.');

INSERT INTO feedback.answered_surveys VALUES (DEFAULT, 1, TO_TIMESTAMP('2013-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));
INSERT INTO feedback.answered_surveys VALUES (DEFAULT, 2, TO_TIMESTAMP('2014-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));
INSERT INTO feedback.answered_surveys VALUES (DEFAULT, 3, TO_TIMESTAMP('2015-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));

INSERT INTO feedback.slider_answers VALUES (DEFAULT, 1, 1, 5);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 2, 2, 5);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 3, 3, 5);

INSERT INTO feedback.text_answers VALUES (DEFAULT, 1, 1, 'Perfect!');
INSERT INTO feedback.text_answers VALUES (DEFAULT, 2, 2, 'Not at all');
INSERT INTO feedback.text_answers VALUES (DEFAULT, 3, 3, 'ASAP');

INSERT INTO feedback.option_answers VALUES (DEFAULT, 1, 1, 2);
INSERT INTO feedback.option_answers VALUES (DEFAULT, 2, 2, 4);
INSERT INTO feedback.option_answers VALUES (DEFAULT, 3, 3, 6);
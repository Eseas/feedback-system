INSERT INTO feedback.potential_users VALUES (DEFAULT, 'user.potential1@mailinator.com');
INSERT INTO feedback.potential_users VALUES (DEFAULT, 'user.potential2@mailinator.com');
INSERT INTO feedback.potential_users VALUES (DEFAULT, 'user.potential3@mailinator.com');

INSERT INTO feedback.users VALUES (DEFAULT, 'user.admin1@mailinator.com', 'User', 'Admin1', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', true, false, 1);
INSERT INTO feedback.users VALUES (DEFAULT, 'user.admin2@mailinator.com', 'User', 'Admin2', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', true, false, 1);
INSERT INTO feedback.users VALUES (DEFAULT, 'user.regular1@mailinator.com', 'User', 'Regular1', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', false, false, 1);
INSERT INTO feedback.users VALUES (DEFAULT, 'user.regular2@mailinator.com', 'User', 'Regular2', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', false, false, 1);

INSERT INTO feedback.surveys VALUES (DEFAULT, 1, FALSE, 'Workplace Confort', 'Please answer a few questions');
INSERT INTO feedback.surveys VALUES (DEFAULT, 1, TRUE,'Salary', 'We kindly ask you to answer some questions');
INSERT INTO feedback.surveys VALUES (DEFAULT, 2, TRUE,'Social Security', 'This survey is anonymous');

INSERT INTO feedback.sections VALUES (DEFAULT, 1, 1, 'First section', 'description 1');
INSERT INTO feedback.sections VALUES (DEFAULT, 1, 2, 'Second section', 'description 2');
INSERT INTO feedback.sections VALUES (DEFAULT, 1, 3, 'Third section', 'description 3');
INSERT INTO feedback.sections VALUES (DEFAULT, 2, 1, 'First section', 'description 1');
INSERT INTO feedback.sections VALUES (DEFAULT, 3, 1, 'First section', 'description 1');


INSERT INTO feedback.slider_questions VALUES (DEFAULT, 1, 1, 1, TRUE, 1, 6, 'Rate office environment');
INSERT INTO feedback.slider_questions VALUES (DEFAULT, 2, 4,3, FALSE, 1, 10, 'Do you like your chair?');
INSERT INTO feedback.slider_questions VALUES (DEFAULT, 3, 5,2, TRUE, 1, 8, 'Do you need a new monitor?');

INSERT INTO feedback.text_questions VALUES (DEFAULT, 1, 2, 1, TRUE, 'Tell us about office environment');
INSERT INTO feedback.text_questions VALUES (DEFAULT, 2, 4, 2, FALSE, 'Do you like your chair?');
INSERT INTO feedback.text_questions VALUES (DEFAULT, 3, 5, 1, TRUE, 'Do you need a new monitor?');

INSERT INTO feedback.checkbox_questions VALUES (DEFAULT, 1, 3, 1, TRUE, 'Which option represent your situation?');
INSERT INTO feedback.checkbox_questions VALUES (DEFAULT, 2, 4, 1,FALSE, 'Do you like your chair?');
INSERT INTO feedback.checkbox_questions VALUES (DEFAULT, 3, 5, 3,TRUE, 'Do you need a new monitor?');

INSERT INTO feedback.checkboxes VALUES (DEFAULT, 1, 'Great');
INSERT INTO feedback.checkboxes VALUES (DEFAULT, 1, 'Medium');
INSERT INTO feedback.checkboxes VALUES (DEFAULT, 1, 'Bad');
INSERT INTO feedback.checkboxes VALUES (DEFAULT, 2, 'Yes');
INSERT INTO feedback.checkboxes VALUES (DEFAULT, 2, 'No');
INSERT INTO feedback.checkboxes VALUES (DEFAULT, 3, 'ASAP');
INSERT INTO feedback.checkboxes VALUES (DEFAULT, 3, 'No.');

INSERT INTO feedback.radio_questions VALUES (DEFAULT, 1, 2, 2, TRUE, 'Which option represent your situation?');
INSERT INTO feedback.radio_questions VALUES (DEFAULT, 2, 4, 4,FALSE, 'Do you like your chair?');
INSERT INTO feedback.radio_questions VALUES (DEFAULT, 3, 5, 4,TRUE, 'Do you need a new monitor?');

INSERT INTO feedback.radio_buttons VALUES (DEFAULT, 1, 'Great');
INSERT INTO feedback.radio_buttons VALUES (DEFAULT, 1, 'Medium');
INSERT INTO feedback.radio_buttons VALUES (DEFAULT, 1, 'Bad');
INSERT INTO feedback.radio_buttons VALUES (DEFAULT, 2, 'Yes');
INSERT INTO feedback.radio_buttons VALUES (DEFAULT, 2, 'No');
INSERT INTO feedback.radio_buttons VALUES (DEFAULT, 3, 'ASAP');
INSERT INTO feedback.radio_buttons VALUES (DEFAULT, 3, 'No.');

INSERT INTO feedback.answered_surveys VALUES (DEFAULT, 1, TO_TIMESTAMP('2013-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));
INSERT INTO feedback.answered_surveys VALUES (DEFAULT, 2, TO_TIMESTAMP('2014-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));
INSERT INTO feedback.answered_surveys VALUES (DEFAULT, 3, TO_TIMESTAMP('2015-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'));

INSERT INTO feedback.slider_answers VALUES (DEFAULT, 1, 1, 5);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 2, 2, 5);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 3, 3, 5);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 1, 1, 1);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 1, 1, 6);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 1, 1, 6);
INSERT INTO feedback.slider_answers VALUES (DEFAULT, 1, 1, 2);

INSERT INTO feedback.text_answers VALUES (DEFAULT, 1, 1, 'Perfect!');
INSERT INTO feedback.text_answers VALUES (DEFAULT, 2, 2, 'Not at all');
INSERT INTO feedback.text_answers VALUES (DEFAULT, 3, 3, 'ASAP');

INSERT INTO feedback.radio_answers VALUES (DEFAULT, 1, 1, 2);
INSERT INTO feedback.radio_answers VALUES (DEFAULT, 2, 2, 5);
INSERT INTO feedback.radio_answers VALUES (DEFAULT, 3, 3, 6);

INSERT INTO feedback.checkbox_answers VALUES (DEFAULT, 1, 1);
INSERT INTO feedback.checkbox_answers VALUES (DEFAULT, 2, 2);
INSERT INTO feedback.checkbox_answers VALUES (DEFAULT, 3, 3);

INSERT INTO feedback.selected_checkboxes VALUES (DEFAULT, 1, 1);
INSERT INTO feedback.selected_checkboxes VALUES (DEFAULT, 1, 2);
INSERT INTO feedback.selected_checkboxes VALUES (DEFAULT, 2, 5);
INSERT INTO feedback.selected_checkboxes VALUES (DEFAULT, 3, 7);
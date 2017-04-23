﻿DROP SCHEMA IF EXISTS feedback CASCADE;

CREATE SCHEMA feedback;

CREATE TABLE feedback.reg_keys
(
  id SERIAL PRIMARY KEY,
  code CHAR(8) NOT NULL,
  used BOOLEAN NOT NULL DEFAULT FALSE,
  expires TIMESTAMP NOT NULL
);

CREATE TABLE feedback.potential_users
(
    id serial PRIMARY KEY,
    email VARCHAR(40)
);

CREATE TABLE feedback.users
(
    id serial PRIMARY KEY,
    email VARCHAR(40),
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    password VARCHAR(20),
    is_admin BOOLEAN,
    is_blocked BOOLEAN,
    opt_lock_version INTEGER
);

CREATE TABLE feedback.surveys
(
    id serial PRIMARY KEY,
    creator_id INTEGER,
    is_confidential BOOLEAN,
    title VARCHAR(200),
    description VARCHAR(200),
    FOREIGN KEY (creator_id) REFERENCES feedback.users (id)
);

CREATE TABLE feedback.slider_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    lower_bound INTEGER,
    upper_bound INTEGER,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);
CREATE TABLE feedback.text_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);
CREATE TABLE feedback.checkbox_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);
CREATE TABLE feedback.radio_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);



CREATE TABLE feedback.checkboxes
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    title VARCHAR(200),
    FOREIGN KEY (question_id) REFERENCES feedback.checkbox_questions (id)
);

CREATE TABLE feedback.radio_buttons
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    title VARCHAR(200),
    FOREIGN KEY (question_id) REFERENCES feedback.radio_questions (id)
);

CREATE TABLE feedback.answered_surveys
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    log_time TIMESTAMP,
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);

CREATE TABLE feedback.slider_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    value INTEGER,
    FOREIGN KEY (question_id) REFERENCES feedback.slider_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.text_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    value VARCHAR(200),
    FOREIGN KEY (question_id) REFERENCES feedback.text_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.radio_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    radio_button_id INTEGER,
    FOREIGN KEY (question_id) REFERENCES feedback.radio_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.checkbox_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    FOREIGN KEY (question_id) REFERENCES feedback.checkbox_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.selected_checkboxes
(
    id serial PRIMARY KEY,
    answer_id INTEGER,
    checkbox_id INTEGER,
    FOREIGN KEY (answer_id) REFERENCES feedback.checkbox_answers (id),
    FOREIGN KEY (checkbox_id) REFERENCES feedback.checkboxes (id)
);
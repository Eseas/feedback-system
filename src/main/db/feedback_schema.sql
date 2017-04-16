DROP SCHEMA IF EXISTS feedback CASCADE;

CREATE SCHEMA feedback;

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
    is_required BOOLEAN,
    lower_bound INTEGER,
    upper_bound INTEGER,
    step INTEGER,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);

CREATE TABLE feedback.text_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    is_required BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);

CREATE TABLE feedback.option_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    is_required BOOLEAN,
    is_multiple BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);

CREATE TABLE feedback.option_values
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    title VARCHAR(200),
    FOREIGN KEY (question_id) REFERENCES feedback.option_questions (id)
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

CREATE TABLE feedback.option_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    option_id INTEGER,
    FOREIGN KEY (question_id) REFERENCES feedback.option_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id),
    FOREIGN KEY (option_id) REFERENCES feedback.option_values (id)
);
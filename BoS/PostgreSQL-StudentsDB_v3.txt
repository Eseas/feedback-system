
CREATE TABLE feedback.emails
(
    id serial PRIMARY KEY,
    email CHARACTER VARYING(20)
);

CREATE TABLE feedback.users
(
    id serial PRIMARY KEY,
    email_id INTEGER,
    first_name CHARACTER VARYING(20),
    last_name CHARACTER VARYING(20),
    password CHARACTER VARYING(20),
    is_admin BOOLEAN,
    is_blocked BOOLEAN,
    opt_lock_version INTEGER,
    FOREIGN KEY (email_id) REFERENCES emails (id)
);

CREATE TABLE feedback.surveys
(
    id serial PRIMARY KEY,
    creator_id INTEGER,
    title CHARACTER VARYING(200),
    is_private BOOLEAN,
    opt_lock_version INTEGER,
    FOREIGN KEY (creator_id) REFERENCES users (id)
);

CREATE TABLE feedback.slider_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    title CHARACTER VARYING(200),
    is_required BOOLEAN,
    lower_bound INTEGER,
    upper_bound INTEGER,
    step INTEGER,
    opt_lock_version INTEGER,
    FOREIGN KEY (survey_id) REFERENCES surveys (id)
);

CREATE TABLE feedback.text_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    title CHARACTER VARYING(200),
    is_required BOOLEAN,
    opt_lock_version INTEGER,
    FOREIGN KEY (survey_id) REFERENCES surveys (id)
);

CREATE TABLE feedback.option_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    title CHARACTER VARYING(200),
    is_required BOOLEAN,
    is_multiple BOOLEAN,
    opt_lock_version INTEGER,
    FOREIGN KEY (survey_id) REFERENCES surveys (id)
);

CREATE TABLE feedback.option_values
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    value CHARACTER VARYING(200),
    opt_lock_version INTEGER,
    FOREIGN KEY (question_id) REFERENCES option_questions (id)
);

CREATE TABLE feedback.answered_surveys
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    log_time TIMESTAMP,
    opt_lock_version INTEGER,
    FOREIGN KEY (survey_id) REFERENCES surveys (id)
);

CREATE TABLE feedback.slider_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    value INTEGER,
    opt_lock_version INTEGER,
    FOREIGN KEY (question_id) REFERENCES slider_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES answered_surveys (id)
);

CREATE TABLE feedback.text_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    value CHARACTER VARYING(200),
    opt_lock_version INTEGER,
    FOREIGN KEY (question_id) REFERENCES text_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES answered_surveys (id)
);

CREATE TABLE feedback.option_answers
(
    id serial PRIMARY KEY,
    question_id INTEGER,
    answered_survey_id INTEGER,
    option_id INTEGER,
    opt_lock_version INTEGER,
    FOREIGN KEY (question_id) REFERENCES option_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES answered_surveys (id),
    FOREIGN KEY (option_id) REFERENCES option_values (id)
);
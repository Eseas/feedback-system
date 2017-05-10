DROP SCHEMA IF EXISTS feedback CASCADE;

CREATE SCHEMA feedback;

CREATE TABLE feedback.potential_users
(
    id serial PRIMARY KEY,
    email VARCHAR(40) UNIQUE NOT NULL
);

CREATE TABLE feedback.users
(
    id serial PRIMARY KEY,
    email VARCHAR(40) UNIQUE NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL ,
    password VARCHAR(60) NOT NULL ,
    is_admin BOOLEAN NOT NULL,
    is_blocked BOOLEAN NOT NULL,
    opt_lock_version INTEGER
);

CREATE TABLE feedback.reg_keys
(
  id SERIAL PRIMARY KEY,
  code CHAR(32) NOT NULL UNIQUE,
  expires TIMESTAMP NOT NULL,
  potential_user_id INT NOT NULL,
  FOREIGN KEY (potential_user_id) REFERENCES feedback.potential_users
);

CREATE TABLE feedback.change_pw_keys
(
  id SERIAL PRIMARY KEY,
  code CHAR(32) NOT NULL UNIQUE,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES feedback.users
);


CREATE TABLE feedback.surveys
(
    id serial PRIMARY KEY,
    link VARCHAR(10) NOT NULL UNIQUE,
    creator_id INTEGER,
    is_confidential BOOLEAN,
    title VARCHAR(200),
    description VARCHAR(200),
    FOREIGN KEY (creator_id) REFERENCES feedback.users (id)
);

CREATE TABLE feedback.sections
(
    id SERIAL PRIMARY KEY,
    survey_id INTEGER,
    position INTEGER,
    title VARCHAR(50),
    description VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys(id)
);

CREATE TABLE feedback.slider_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    section_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    lower_bound INTEGER,
    upper_bound INTEGER,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
);

CREATE TABLE feedback.text_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    section_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
);

CREATE TABLE feedback.checkbox_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    section_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
);

CREATE TABLE feedback.radio_questions
(
    id serial PRIMARY KEY,
    survey_id INTEGER,
    section_id INTEGER,
    position INTEGER,
    is_required BOOLEAN,
    title VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
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
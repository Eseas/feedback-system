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
    creator_id INTEGER NOT NULL,
    is_confidential BOOLEAN NOT NULL,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(200),
    FOREIGN KEY (creator_id) REFERENCES feedback.users (id)
);

CREATE TABLE feedback.sections
(
  id          SERIAL PRIMARY KEY,
  survey_id   INTEGER NOT NULL,
  position    INTEGER NOT NULL,
  title       VARCHAR(50),
  description VARCHAR(200),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys(id)
);

CREATE TABLE feedback.slider_questions
(
  id          SERIAL PRIMARY KEY,
  survey_id   INTEGER      NOT NULL,
  section_id  INTEGER      NOT NULL,
  position    INTEGER      NOT NULL,
  is_required BOOLEAN      NOT NULL,
  lower_bound INTEGER      NOT NULL,
  upper_bound INTEGER      NOT NULL,
  title       VARCHAR(200) NOT NULL,
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
);

CREATE TABLE feedback.text_questions
(
  id          SERIAL PRIMARY KEY,
  survey_id   INTEGER      NOT NULL,
  section_id  INTEGER      NOT NULL,
  position    INTEGER      NOT NULL,
  is_required BOOLEAN      NOT NULL,
  title       VARCHAR(200) NOT NULL,
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
);

CREATE TABLE feedback.checkbox_questions
(
  id          SERIAL PRIMARY KEY,
  survey_id   INTEGER      NOT NULL,
  section_id  INTEGER      NOT NULL,
  position    INTEGER      NOT NULL,
  is_required BOOLEAN      NOT NULL,
  title       VARCHAR(200) NOT NULL,
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
);

CREATE TABLE feedback.radio_questions
(
  id          SERIAL PRIMARY KEY,
  survey_id   INTEGER      NOT NULL,
  section_id  INTEGER      NOT NULL,
  position    INTEGER      NOT NULL,
  is_required BOOLEAN      NOT NULL,
  title       VARCHAR(200) NOT NULL,
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id),
    FOREIGN KEY (section_id) REFERENCES feedback.sections (id)
);

CREATE TABLE feedback.checkboxes
(
  id          SERIAL PRIMARY KEY,
  question_id INTEGER      NOT NULL,
  title       VARCHAR(200) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES feedback.checkbox_questions (id)
);

CREATE TABLE feedback.radio_buttons
(
  id          SERIAL PRIMARY KEY,
  question_id INTEGER      NOT NULL,
  title       VARCHAR(200) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES feedback.radio_questions (id)
);

CREATE TABLE feedback.answered_surveys
(
  id        SERIAL PRIMARY KEY,
  survey_id INTEGER   NOT NULL,
  log_time  TIMESTAMP NOT NULL DEFAULT now(),
    FOREIGN KEY (survey_id) REFERENCES feedback.surveys (id)
);

CREATE TABLE feedback.slider_answers
(
  id                 SERIAL PRIMARY KEY,
  question_id        INTEGER NOT NULL,
  answered_survey_id INTEGER NOT NULL,
  value              INTEGER NOT NULL,
    FOREIGN KEY (question_id) REFERENCES feedback.slider_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.text_answers
(
  id                 SERIAL PRIMARY KEY,
  question_id        INTEGER      NOT NULL,
  answered_survey_id INTEGER      NOT NULL,
  value              VARCHAR(300) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES feedback.text_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.radio_answers
(
  id                 SERIAL PRIMARY KEY,
  question_id        INTEGER NOT NULL,
  answered_survey_id INTEGER NOT NULL,
  radio_button_id    INTEGER NOT NULL,
    FOREIGN KEY (question_id) REFERENCES feedback.radio_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.checkbox_answers
(
  id                 SERIAL PRIMARY KEY,
  question_id        INTEGER NOT NULL,
  answered_survey_id INTEGER NOT NULL,
    FOREIGN KEY (question_id) REFERENCES feedback.checkbox_questions (id),
    FOREIGN KEY (answered_survey_id) REFERENCES feedback.answered_surveys (id)
);

CREATE TABLE feedback.selected_checkboxes
(
  id          SERIAL PRIMARY KEY,
  answer_id   INTEGER NOT NULL,
  checkbox_id INTEGER NOT NULL,
    FOREIGN KEY (answer_id) REFERENCES feedback.checkbox_answers (id),
    FOREIGN KEY (checkbox_id) REFERENCES feedback.checkboxes (id)
);

CREATE TABLE feedback.logs
(
    id serial PRIMARY KEY,
    time TIMESTAMP NOT NULL,
    text VARCHAR(200) NOT NULL
);
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = feedback, pg_catalog;

--
-- Data for Name: users; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO users (id, email, first_name, last_name, password, is_admin, is_blocked, opt_lock_version) VALUES (1, 'user.admin1@mailinator.com', 'User', 'Admin1', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', true, false, 1);
INSERT INTO users (id, email, first_name, last_name, password, is_admin, is_blocked, opt_lock_version) VALUES (3, 'user.regular1@mailinator.com', 'User', 'Regular1', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', false, false, 1);
INSERT INTO users (id, email, first_name, last_name, password, is_admin, is_blocked, opt_lock_version) VALUES (4, 'user.regular2@mailinator.com', 'User', 'Regular2', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', false, false, 1);
INSERT INTO users (id, email, first_name, last_name, password, is_admin, is_blocked, opt_lock_version) VALUES (2, 'user.admin2@mailinator.com', 'User', 'Admin2', '$2a$10$5z9BfOrt7nIRSR6boXZ5G.6jiuVHLCDxj7GChwJ8b9aWvr0ksS95q', true, true, 2);


--
-- Data for Name: surveys; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO surveys (id, link, creator_id, is_confidential, title, description) VALUES (1, 'c4ca4238a0', 1, false, 'Workplace Confort', 'Please answer a few questions');
INSERT INTO surveys (id, link, creator_id, is_confidential, title, description) VALUES (2, 'c4ca4238a1', 1, true, 'Salary', 'We kindly ask you to answer some questions');
INSERT INTO surveys (id, link, creator_id, is_confidential, title, description) VALUES (3, 'c4ca4238a2', 2, true, 'Social Security', 'This survey is anonymous');
INSERT INTO surveys (id, link, creator_id, is_confidential, title, description) VALUES (4, 'mlrwrTtzKi', 1, true, 'Catering services', '');
INSERT INTO surveys (id, link, creator_id, is_confidential, title, description) VALUES (5, '59U4JIkaS2', 1, true, 'Pets', '');


INSERT INTO sections (id, survey_id, "position", title, description) VALUES (1, 1, 1, 'First section', 'description 1');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (2, 1, 2, 'Second section', 'description 2');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (3, 1, 3, 'Third section', 'description 3');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (4, 2, 1, 'First section', 'description 1');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (5, 3, 1, 'First section', 'description 1');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (6, 4, 1, 'General information', '');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (7, 4, 2, 'Food preferences', '');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (8, 5, 1, 'General', '');
INSERT INTO sections (id, survey_id, "position", title, description) VALUES (9, 5, 2, 'Others', '');


--
-- Data for Name: checkbox_questions; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO checkbox_questions (id, survey_id, section_id, "position", is_required, title) VALUES (1, 1, 3, 1, true, 'Which option represent your situation?');
INSERT INTO checkbox_questions (id, survey_id, section_id, "position", is_required, title) VALUES (2, 2, 4, 1, false, 'Do you like your chair?');
INSERT INTO checkbox_questions (id, survey_id, section_id, "position", is_required, title) VALUES (3, 3, 5, 3, true, 'Do you need a new monitor?');
INSERT INTO checkbox_questions (id, survey_id, section_id, "position", is_required, title) VALUES (4, 4, 7, 2, true, 'What food you are allergic to?');
INSERT INTO checkbox_questions (id, survey_id, section_id, "position", is_required, title) VALUES (5, 5, 9, 1, false, 'Which pets do you own or have owned in the past?');


--
-- Name: checkbox_questions_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('checkbox_questions_id_seq', 5, true);


--
-- Data for Name: checkboxes; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO checkboxes (id, question_id, title) VALUES (1, 1, 'Great');
INSERT INTO checkboxes (id, question_id, title) VALUES (2, 1, 'Medium');
INSERT INTO checkboxes (id, question_id, title) VALUES (3, 1, 'Bad');
INSERT INTO checkboxes (id, question_id, title) VALUES (4, 2, 'Yes');
INSERT INTO checkboxes (id, question_id, title) VALUES (5, 2, 'No');
INSERT INTO checkboxes (id, question_id, title) VALUES (6, 3, 'ASAP');
INSERT INTO checkboxes (id, question_id, title) VALUES (7, 3, 'No.');
INSERT INTO checkboxes (id, question_id, title) VALUES (8, 4, 'eggs');
INSERT INTO checkboxes (id, question_id, title) VALUES (9, 4, 'milk');
INSERT INTO checkboxes (id, question_id, title) VALUES (10, 4, 'peanuts');
INSERT INTO checkboxes (id, question_id, title) VALUES (11, 4, 'fish');
INSERT INTO checkboxes (id, question_id, title) VALUES (12, 4, 'other');
INSERT INTO checkboxes (id, question_id, title) VALUES (13, 5, 'Dog');
INSERT INTO checkboxes (id, question_id, title) VALUES (14, 5, 'Cat');
INSERT INTO checkboxes (id, question_id, title) VALUES (15, 5, 'Hamster');
INSERT INTO checkboxes (id, question_id, title) VALUES (16, 5, 'Fish');
INSERT INTO checkboxes (id, question_id, title) VALUES (17, 5, 'Snake');
INSERT INTO checkboxes (id, question_id, title) VALUES (18, 5, 'Lizard');
INSERT INTO checkboxes (id, question_id, title) VALUES (19, 5, 'Other');


--
-- Name: checkboxes_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('checkboxes_id_seq', 19, true);


--
-- Data for Name: potential_users; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO potential_users (id, email) VALUES (1, 'user.potential1@mailinator.com');
INSERT INTO potential_users (id, email) VALUES (2, 'user.potential2@mailinator.com');
INSERT INTO potential_users (id, email) VALUES (3, 'user.potential3@mailinator.com');


--
-- Name: potential_users_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('potential_users_id_seq', 3, true);


--
-- Data for Name: radio_questions; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (1, 1, 2, 2, true, 'Which option represent your situation?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (2, 2, 4, 4, false, 'Do you like your chair?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (3, 3, 5, 4, true, 'Do you need a new monitor?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (4, 4, 6, 1, true, 'Would you like for the office to have a canteen?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (5, 4, 6, 4, false, 'How often would you eat in the office canteen?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (6, 4, 6, 5, false, 'For how long should the canteen be open?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (7, 4, 7, 1, false, 'Would you like to see vegan options?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (8, 5, 8, 2, false, 'How many pets do you own?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (9, 5, 8, 3, false, 'Would you like to see office events dedicated to pets?');
INSERT INTO radio_questions (id, survey_id, section_id, "position", is_required, title) VALUES (10, 5, 9, 2, false, 'Are you allergic animals?');


--
-- Data for Name: radio_buttons; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO radio_buttons (id, question_id, title) VALUES (1, 1, 'Great');
INSERT INTO radio_buttons (id, question_id, title) VALUES (2, 1, 'Medium');
INSERT INTO radio_buttons (id, question_id, title) VALUES (3, 1, 'Bad');
INSERT INTO radio_buttons (id, question_id, title) VALUES (4, 2, 'Yes');
INSERT INTO radio_buttons (id, question_id, title) VALUES (5, 2, 'No');
INSERT INTO radio_buttons (id, question_id, title) VALUES (6, 3, 'ASAP');
INSERT INTO radio_buttons (id, question_id, title) VALUES (7, 3, 'No.');
INSERT INTO radio_buttons (id, question_id, title) VALUES (8, 4, 'Yes');
INSERT INTO radio_buttons (id, question_id, title) VALUES (9, 4, 'No');
INSERT INTO radio_buttons (id, question_id, title) VALUES (10, 4, 'Maybe');
INSERT INTO radio_buttons (id, question_id, title) VALUES (11, 5, 'Never');
INSERT INTO radio_buttons (id, question_id, title) VALUES (12, 5, 'Once a week');
INSERT INTO radio_buttons (id, question_id, title) VALUES (13, 5, 'One to three time a week');
INSERT INTO radio_buttons (id, question_id, title) VALUES (14, 5, 'More than three times a week');
INSERT INTO radio_buttons (id, question_id, title) VALUES (15, 6, 'From 8 am to 14 pm');
INSERT INTO radio_buttons (id, question_id, title) VALUES (16, 6, 'From 10 am to 14 pm');
INSERT INTO radio_buttons (id, question_id, title) VALUES (17, 6, 'From 8 am to 16 pm');
INSERT INTO radio_buttons (id, question_id, title) VALUES (18, 7, 'Yes');
INSERT INTO radio_buttons (id, question_id, title) VALUES (19, 7, 'No');
INSERT INTO radio_buttons (id, question_id, title) VALUES (20, 7, 'Maybe');
INSERT INTO radio_buttons (id, question_id, title) VALUES (21, 8, 'None');
INSERT INTO radio_buttons (id, question_id, title) VALUES (22, 8, '1');
INSERT INTO radio_buttons (id, question_id, title) VALUES (23, 8, '1 to 3');
INSERT INTO radio_buttons (id, question_id, title) VALUES (24, 8, 'More than 3');
INSERT INTO radio_buttons (id, question_id, title) VALUES (25, 9, 'Yes');
INSERT INTO radio_buttons (id, question_id, title) VALUES (26, 9, 'No');
INSERT INTO radio_buttons (id, question_id, title) VALUES (27, 9, 'Maybe');
INSERT INTO radio_buttons (id, question_id, title) VALUES (28, 10, 'Yes');
INSERT INTO radio_buttons (id, question_id, title) VALUES (29, 10, 'No');


--
-- Name: radio_buttons_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('radio_buttons_id_seq', 29, true);


--
-- Name: radio_questions_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('radio_questions_id_seq', 10, true);


--
-- Data for Name: reg_keys; Type: TABLE DATA; Schema: feedback; Owner: mang07
--



--
-- Name: reg_keys_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('reg_keys_id_seq', 1, false);


--
-- Name: sections_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('sections_id_seq', 9, true);



--
-- Data for Name: slider_questions; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO slider_questions (id, survey_id, section_id, "position", is_required, lower_bound, upper_bound, title) VALUES (1, 1, 1, 1, true, 1, 6, 'Rate office environment');
INSERT INTO slider_questions (id, survey_id, section_id, "position", is_required, lower_bound, upper_bound, title) VALUES (2, 2, 4, 3, false, 1, 10, 'Do you like your chair?');
INSERT INTO slider_questions (id, survey_id, section_id, "position", is_required, lower_bound, upper_bound, title) VALUES (3, 3, 5, 2, true, 1, 8, 'Do you need a new monitor?');
INSERT INTO slider_questions (id, survey_id, section_id, "position", is_required, lower_bound, upper_bound, title) VALUES (4, 4, 6, 2, false, 1, 10, 'How much would you pay for full course dinner? (eur)');
INSERT INTO slider_questions (id, survey_id, section_id, "position", is_required, lower_bound, upper_bound, title) VALUES (5, 4, 6, 3, false, 1, 4, 'In which floor should the canteen be?');


--
-- Name: slider_questions_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('slider_questions_id_seq', 5, true);


--
-- Name: surveys_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('surveys_id_seq', 5, true);


--
-- Data for Name: text_questions; Type: TABLE DATA; Schema: feedback; Owner: mang07
--

INSERT INTO text_questions (id, survey_id, section_id, "position", is_required, title) VALUES (1, 1, 2, 1, true, 'Tell us about office environment');
INSERT INTO text_questions (id, survey_id, section_id, "position", is_required, title) VALUES (2, 2, 4, 2, false, 'Do you like your chair?');
INSERT INTO text_questions (id, survey_id, section_id, "position", is_required, title) VALUES (3, 3, 5, 1, true, 'Do you need a new monitor?');
INSERT INTO text_questions (id, survey_id, section_id, "position", is_required, title) VALUES (4, 5, 8, 1, false, 'Are you for or against pets in the office and why?');


--
-- Name: text_questions_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('text_questions_id_seq', 4, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: feedback; Owner: mang07
--

SELECT pg_catalog.setval('users_id_seq', 4, true);


--
-- PostgreSQL database dump complete
--


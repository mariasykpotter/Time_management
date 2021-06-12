DROP TABLE ROLE CASCADE;
DROP TABLE PERSON CASCADE;
DROP TABLE CATEGORY CASCADE;
DROP TABLE ACTIVITY;
DROP TABLE TIME_LOG;
----------------------------------------------------------------
-- ROLE
----------------------------------------------------------------
CREATE TABLE ROLE
(
    id   SERIAL      NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE
);
----------------------------------------------------------------
INSERT INTO ROLE
VALUES (0, 'admin');
INSERT INTO ROLE
VALUES (1, 'user');
SELECT *
FROM ROLE;
----------------------------------------------------------------
-- PERSON
----------------------------------------------------------------
CREATE TABLE PERSON
(
    id          serial PRIMARY KEY,
    first_name  varchar(25)  NOT NULL,
    last_name   varchar(25)  NOT NULL,
    user_name   varchar(25)  NOT NULL UNIQUE,
    role_id     int          NOT NULL REFERENCES ROLE (id) ON DELETE CASCADE,
    locale_name varchar(25)  NOT NULL,
    password    varchar(500) NOT NULL
);
DROP TABLE PERSON;
INSERT INTO PERSON
VALUES (DEFAULT, 'Andriy', 'Petrov', 'a_petov', 0, DEFAULT, 'd6e0c79545eaf86e7214a41af1d97814');
----------------------------------------------------------------
-- CATEGORY
----------------------------------------------------------------
CREATE TABLE CATEGORY
(
    id            serial PRIMARY KEY,
    category_name varchar(25) NOT NULL UNIQUE
);
INSERT INTO CATEGORY
VALUES (1, 'спорт');
INSERT INTO CATEGORY
VALUES (2, 'подорожі');
INSERT INTO CATEGORY
VALUES (3, 'танці');
INSERT INTO CATEGORY
VALUES (4, 'музика');
INSERT INTO CATEGORY
VALUES (5, 'нетворкінг');
INSERT INTO CATEGORY
VALUES (6, 'шопінг');
INSERT INTO PERSON
VALUES (DEFAULT, 'p', 'l', 'pl', 1, DEFAULT, '123');
----------------------------------------------------------------
-- ACTIVITY
----------------------------------------------------------------
CREATE TABLE ACTIVITY
(
    id            serial PRIMARY KEY,
    activity_name varchar(25) NOT NULL,
    category_id   int         NOT NULL REFERENCES CATEGORY (id) ON DELETE CASCADE
);
SELECT *
FROM ACTIVITY;
INSERT INTO ACTIVITY
VALUES (1, 'плавання у басейні', 1);
INSERT INTO ACTIVITY
VALUES (2, 'заняття з танців', 3);
INSERT INTO ACTIVITY
VALUES (3, 'співи у хорі', 4);
INSERT INTO ACTIVITY
VALUES (4, 'вечірка', 5);
INSERT INTO ACTIVITY
VALUES (5, 'закупівля продуктів', 6);
----------------------------------------------------------------
-- TIME_LOG
----------------------------------------------------------------
CREATE TABLE TIME_LOG
(
    id          serial PRIMARY KEY,
    user_id     int   NOT NULL references Person (id) ON DELETE CASCADE,
    activity_id int   NOT NULL references Activity (id) ON DELETE CASCADE,
    start_at    time  NOT NULL,
    end_at      time  NOT NULL,
    duration    float NOT NULL,
    status      int   NOT NULL
);
----------------------------------------------------------------
SELECT *
FROM PERSON;
SELECT *
FROM ACTIVITY;
SELECT *
FROM CATEGORY;
SELECT *
FROM TIME_LOG;
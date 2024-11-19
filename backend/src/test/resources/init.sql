CREATE TABLE agents (
    agent_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    has_access BOOLEAN NOT NULL,
    tier VARCHAR(50),
    selected_state VARCHAR(50),
    time_inserted TIMESTAMP(1)
);

SELECT pg_sleep(.2);

CREATE TABLE clients (
    client_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_of_agent BIGINT DEFAULT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    searched_state VARCHAR(50),
    searched_county VARCHAR(100),
    searched_place VARCHAR(100),
    time_inserted TIMESTAMP(1),
    CONSTRAINT fk_id_of_agent
        FOREIGN KEY (id_of_agent)
            REFERENCES agents(agent_id) ON DELETE SET DEFAULT
);

SELECT pg_sleep(.2);

CREATE TABLE counties (
    county_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    county_name VARCHAR(100),
    state_name VARCHAR(100),
    UNIQUE (county_name, state_name)
);

SELECT pg_sleep(.2);

CREATE TABLE county_agents (
    county_agent_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_of_agent BIGINT,
    id_of_county BIGINT,
    FOREIGN KEY (id_of_agent)
        REFERENCES agents(agent_id) ON DELETE CASCADE,
    FOREIGN KEY (id_of_county)
        REFERENCES counties(county_id)
);

SELECT pg_sleep(.2);

INSERT INTO agents (first_name, last_name, email, phone_number, has_access, tier, selected_state, time_inserted)
VALUES
('Alice', 'Taylor', 'alice.taylor@example.com', '301-555-0123', true, 'basic', 'MD', '2024-11-04 23:12:31.515611342'),
('Brian', 'Wilson', 'brian.wilson@example.com', '410-555-0198', true, 'premium', 'MD', '2024-11-04 23:25:31.515611342');

SELECT pg_sleep(.2);

INSERT INTO clients (id_of_agent, first_name, last_name, email, phone_number, searched_state, searched_county, searched_place, time_inserted)
VALUES
    (1, 'Emily', 'Johnson', 'emily.johnson@example.com', '301-555-0171', 'MD', 'Montgomery', 'Rockville', '2024-11-04 23:12:31.515611342'),
    (1, 'Michael', 'Smith', 'michael.smith@example.com', '410-555-0145', 'MD', 'Anne Arundel', 'Annapolis', '2024-11-04 23:13:31.515611342'),
    (1, 'Sarah', 'Davis', 'sarah.davis@example.com', '240-555-0199', 'MD', 'Prince Georges', 'Bowie', '2024-11-04 23:14:31.515611342'),
    (2, 'David', 'Brown', 'david.brown@example.com', '443-555-0123', 'MD', 'Baltimore', 'Baltimore', '2024-11-04 23:15:31.515611342'),
    (2, 'Jessica', 'Garcia', 'jessica.garcia@example.com', '301-555-0188', 'MD', 'Howard', 'Ellicott City', '2024-11-04 23:16:31.515611342');

SELECT pg_sleep(.2);

INSERT INTO counties (county_name, state_name)
VALUES
('Montgomery', 'MD'),
('Anne Arundel', 'MD'),
('Prince Georges', 'MD'),
('Baltimore', 'MD'),
('Howard', 'MD');

SELECT pg_sleep(.2);

INSERT INTO county_agents (id_of_agent, id_of_county)
VALUES
(1,1),
(1,2),
(1,3),
(2,3),
(2,4),
(2,5);









CREATE TABLE agents (
                        agent_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        first_name VARCHAR(100),
                        last_name VARCHAR(100),
                        email VARCHAR(255) UNIQUE,
                        phone_number VARCHAR(20),
                        has_access BOOLEAN NOT NULL,
                        tier VARCHAR(50),
                        selected_state VARCHAR(50),
                        time_inserted TIMESTAMP(1)
);

SELECT pg_sleep(.2);

CREATE TABLE clients (
                         client_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         id_of_agent BIGINT DEFAULT NULL,
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         email VARCHAR(255) UNIQUE,
                         phone_number VARCHAR(20),
                         searched_state VARCHAR(50),
                         searched_county VARCHAR(100),
                         searched_place VARCHAR(100),
                         time_inserted TIMESTAMP(1),
                         CONSTRAINT fk_id_of_agent
                             FOREIGN KEY (id_of_agent)
                                 REFERENCES agents(agent_id) ON DELETE SET DEFAULT
);

SELECT pg_sleep(.2);

CREATE TABLE counties (
                          county_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          county_name VARCHAR(100),
                          state_name VARCHAR(100),
                          UNIQUE (county_name, state_name)
);

SELECT pg_sleep(.2);

CREATE TABLE county_agents (
                               county_agent_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                               id_of_agent BIGINT,
                               id_of_county BIGINT,
                               FOREIGN KEY (id_of_agent)
                                   REFERENCES agents(agent_id) ON DELETE CASCADE,
                               FOREIGN KEY (id_of_county)
                                   REFERENCES counties(county_id)
);

SELECT pg_sleep(.2);

INSERT INTO agents (first_name, last_name, email, phone_number, has_access, tier, selected_state, time_inserted)
VALUES
    ('Alice', 'Taylor', 'alice.taylor@example.com', '301-555-0123', true, 'basic', 'MD', '2024-11-04 23:12:31.515611342'),
    ('Brian', 'Wilson', 'brian.wilson@example.com', '410-555-0198', true, 'premium', 'MD', '2024-11-04 23:25:31.515611342');

SELECT pg_sleep(.2);

INSERT INTO clients (id_of_agent, first_name, last_name, email, phone_number, searched_state, searched_county, searched_place, time_inserted)
VALUES
    (1, 'Emily', 'Johnson', 'emily.johnson@example.com', '301-555-0171', 'MD', 'Montgomery', 'Rockville', '2024-11-04 23:12:31.515611342'),
    (1, 'Michael', 'Smith', 'michael.smith@example.com', '410-555-0145', 'MD', 'Anne Arundel', 'Annapolis', '2024-11-04 23:13:31.515611342'),
    (1, 'Sarah', 'Davis', 'sarah.davis@example.com', '240-555-0199', 'MD', 'Prince George''s', 'Bowie', '2024-11-04 23:14:31.515611342'),
    (2, 'David', 'Brown', 'david.brown@example.com', '443-555-0123', 'MD', 'Baltimore', 'Baltimore', '2024-11-04 23:15:31.515611342'),
    (2, 'Jessica', 'Garcia', 'jessica.garcia@example.com', '301-555-0188', 'MD', 'Howard', 'Ellicott City', '2024-11-04 23:16:31.515611342');

SELECT pg_sleep(.2);

INSERT INTO counties (county_name, state_name)
VALUES
    ('Montgomery', 'MD'),
    ('Anne Arundel', 'MD'),
    ('Prince George''s', 'MD'),
    ('Baltimore', 'MD'),
    ('Howard', 'MD');

SELECT pg_sleep(.2);

INSERT INTO county_agents (id_of_agent, id_of_county)
VALUES
    (1,1),
    (1,2),
    (1,3),
    (2,3),
    (2,4),
    (2,5);
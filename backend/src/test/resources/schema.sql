CREATE TABLE IF NOT EXISTS agents (
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

CREATE TABLE IF NOT EXISTS clients (
                         client_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         id_of_agent BIGINT DEFAULT NULL,
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         email VARCHAR(255) UNIQUE,
                         phone_number VARCHAR(20),
                         searched_state VARCHAR(50),
                         searched_county VARCHAR(100),
                         searched_place VARCHAR(100),
                         CONSTRAINT fk_id_of_agent
                             FOREIGN KEY (id_of_agent)
                                 REFERENCES agents(agent_id) ON DELETE SET DEFAULT
);

SELECT pg_sleep(.2);

CREATE TABLE IF NOT EXISTS counties (
                          county_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          county_name VARCHAR(100),
                          state_name VARCHAR(100),
                          UNIQUE (county_name, state_name)
);

SELECT pg_sleep(.2);

CREATE TABLE IF NOT EXISTS county_agents (
                               county_agent_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                               id_of_agent BIGINT,
                               id_of_county BIGINT,
                               FOREIGN KEY (id_of_agent)
                                   REFERENCES agents(agent_id) ON DELETE CASCADE,
                               FOREIGN KEY (id_of_county)
                                   REFERENCES counties(county_id)
);



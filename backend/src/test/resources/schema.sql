CREATE TABLE agents (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    has_access BOOLEAN NOT NULL,
    tier VARCHAR(50),
    selected_state VARCHAR(50),
    selected_counties VARCHAR(100)[],
    client_ids BIGINT[]
);

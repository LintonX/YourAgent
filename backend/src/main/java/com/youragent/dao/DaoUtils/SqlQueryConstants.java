package com.youragent.dao.DaoUtils;

public class SqlQueryConstants {

    public static final String AGENT_SAVE_SQL_QUERY = """
            INSERT INTO agents (%s, %s, %s, %s, %s, %s, %s, %s)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING agent_id;
            """;

//    public static final String AGENT_GET_SQL_QUERY = """
//                                SELECT agent_id,
//                                    agents.first_name as agent_first_name,
//                                    agents.last_name as agent_last_name,
//                                    agents.email as agent_email,
//                                    agents.phone_number as agent_phone_number,
//                                    agents.has_access, agents.tier,
//                                    agents.selected_state,
//                                    clients.client_id,
//                                    clients.id_of_agent,
//                                    clients.first_name as client_first_name,
//                                    clients.last_name as client_last_name,
//                                    clients.email as client_email,
//                                    clients.phone_number as client_phone_number,
//                                    clients.searched_state,
//                                    clients.searched_county,
//                                    clients.searched_place
//                                FROM agents
//                                LEFT JOIN clients
//                                ON agents.agent_id = clients.id_of_agent
//                                WHERE agents.agent_id = ?;
//                                """;
    public static final String AGENT_GET_SQL_QUERY = """
                SELECT
                    agents.agent_id,
                    agents.first_name AS agent_first_name,
                    agents.last_name AS agent_last_name,
                    agents.email AS agent_email,
                    agents.phone_number AS agent_phone_number,
                    agents.has_access,
                    agents.tier,
                    agents.selected_state,
                    array_to_json(array_agg(DISTINCT jsonb_build_object(
                        'client_id', clients.client_id,
                        'client_id_of_agent', clients.id_of_agent,
                        'client_first_name', clients.first_name,
                        'client_last_name', clients.last_name,
                        'client_email', clients.email,
                        'client_phone_number', clients.phone_number,
                        'client_searched_state', clients.searched_state,
                        'client_searched_county', clients.searched_county,
                        'client_searched_place', clients.searched_place,
                        'client_time_inserted', clients.time_inserted
                    ))) AS client_objects,
                    array_to_json(array_agg(DISTINCT jsonb_build_object(
                        'county_name', counties.county_name,
                        'state_name', counties.state_name
                    ))) AS county_objects
                FROM
                    agents
                LEFT JOIN
                    clients ON agents.agent_id = clients.id_of_agent
                LEFT JOIN
                    county_agents ON county_agents.id_of_agent = agents.agent_id
                LEFT JOIN
                    counties ON county_agents.id_of_county = counties.county_id
                WHERE
                    agents.agent_id = ?
                GROUP BY
                    agents.agent_id;
                """;

    public static final String AGENT_DELETE_SQL_QUERY = "DELETE FROM agents WHERE agent_id = ?;";

    public static final String AGENT_UPDATE_SQL_QUERY = """
                        UPDATE agents
                        SET %s
                        WHERE agent_id = ?;
            """;

    public static final String AGENT_GET_NEXT_AGENT_FROM_COUNTY_QUEUE_SQL_QUERY = """
        SELECT agents.agent_id
        FROM agents
        LEFT JOIN county_agents ON county_agents.id_of_agent = agents.agent_id
        LEFT JOIN counties ON counties.county_id = county_agents.id_of_county
        WHERE counties.county_name = ? and counties.state_name = ?
        ORDER BY agents.time_inserted ASC
        LIMIT 1;
        """;

    public static final String AGENT_SAVE_COUNTY_SQL_QUERY = """
            INSERT INTO counties (county_name, state_name)
            VALUES (?, ?)
            ON CONFLICT (county_name, state_name)
            DO UPDATE SET county_name = counties.county_name
            RETURNING county_id;
            """;

    public static final String AGENT_MAP_COUNTY_TO_AGENT_SQL_QUERY = """
            INSERT INTO county_agents (id_of_agent, id_of_county)
            VALUES (?, ?)
            RETURNING county_agent_id;
            """;

    //-------------------------------------------------------------------------------------------------------------

    public static final String AGENT_CREDENTIALS_SAVE_CREDENTIALS_SQL_QUERY = """
            INSERT INTO agent_credentials (%s, %s)
            VALUES (?, ?)
            RETURNING cred_id;
            """;

    public static final String AGENT_CREDENTIALS_GET_AGENT_CREDENTIALS_SQL_QUERY = """
            SELECT cred_id, pass, id_of_agent
            FROM agent_credentials
            WHERE agent_credentials.email = ?
            """;

    public static final String AGENT_CREDENTIALS_UPDATE_SQL_QUERY = """
                        UPDATE agent_credentials
                        SET %s
                        WHERE cred_id = ?;
            """;

    //-------------------------------------------------------------------------------------------------------------

    public static final String CLIENT_SAVE_SQL_QUERY = """
            INSERT INTO clients (%s, %s, %s, %s, %s, %s, %s, %s)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING client_id;
            """;

    public static final String CLIENT_GET_SQL_QUERY = """
                                    SELECT
                                    clients.client_id,
                                    clients.id_of_agent,
                                    clients.first_name as client_first_name,
                                    clients.last_name as client_last_name,
                                    clients.email as client_email,
                                    clients.phone_number as client_phone_number,
                                    clients.searched_state,
                                    clients.searched_county,
                                    clients.searched_place,
                                    clients.time_inserted
                                    FROM clients
                                    WHERE client_id = ?;
                                    """;

    public static final String CLIENT_UPDATE_SQL_QUERY = """
                        UPDATE clients
                        SET %s
                        WHERE client_id = ?;
                        """;

}

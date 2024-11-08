package com.youragent.dao;

public class SqlQueryConstants {

    public static final String AGENT_SAVE_SQL_QUERY = """
            INSERT INTO agents (%s, %s, %s, %s, %s, %s, %s, %s) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
            "RETURNING agent_id;""";

    public static final String AGENT_GET_SQL_QUERY = """
                                SELECT agent_id,
                                    agents.first_name as agent_first_name,
                                    agents.last_name as agent_last_name,
                                    agents.email as agent_email,
                                    agents.phone_number as agent_phone_number,
                                    agents.has_access, agents.tier,
                                    agents.selected_state,
                                    clients.client_id,
                                    clients.id_of_agent,
                                    clients.first_name as client_first_name,
                                    clients.last_name as client_last_name,
                                    clients.email as client_email,
                                    clients.phone_number as client_phone_number,
                                    clients.searched_state,
                                    clients.searched_county,
                                    clients.searched_place
                                FROM agents
                                LEFT JOIN clients
                                ON agents.agent_id = clients.id_of_agent
                                WHERE agents.agent_id = ?;""";

    public static final String AGENT_DELETE_SQL_QUERY = "DELETE FROM agents WHERE agent_id = ?;";

    public static final String CLIENT_SAVE_SQL_QUERY = """
            INSERT INTO clients (%s %s %s %s %s %s %s)
            VALUES (? ? ? ? ? ? ?);
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
                                    clients.searched_place
                                    FROM clients
                                    WHERE client_id = ?;
                                    """;

    public static final String CLIENT_UPDATE_SQL_QUERY = """
                        UPDATE clients
                        SET %s
                        WHERE client_id = ?;
            """;
}

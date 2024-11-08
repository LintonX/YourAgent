package com.youragent.dao.mapper;

import com.youragent.model.Agent;
import com.youragent.model.AgentTier;
import com.youragent.model.Client;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class AgentMapper implements ResultSetExtractor<Agent>, RowMapper<Agent> {

    private final ClientMapper clientMapper;

    public AgentMapper(@NonNull final ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @Override
    public Agent extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Agent agent = null;
        Set<Client> clientSet = new HashSet<>();

        while (resultSet.next()) {
            log.info(resultSet.toString());

            long agentId = resultSet.getLong("agent_id");

            if (agent == null && agentId != 0) {
                agent = mapRow(resultSet, resultSet.getRow());
            }

            long clientId = resultSet.getLong("client_id");

            if (clientId != 0) {
                final Client client = clientMapper.mapRow(resultSet, resultSet.getRow());
                clientSet.add(client);
            }
        }

        if (agent != null) {
            agent.setClients(clientSet.stream().toList());
        } else {
            throw new IllegalStateException("No agent present after iterating over result set");
        }

        return agent;
    }

    @Override
    public Agent mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Agent.builder()
                .id(resultSet.getLong("agent_id"))
                .firstName(resultSet.getString("agent_first_name"))
                .lastName(resultSet.getString("agent_last_name"))
                .email(resultSet.getString("agent_email"))
                .phoneNumber(resultSet.getString("agent_phone_number"))
                .hasAcces(resultSet.getBoolean("has_access"))
                .tier(AgentTier.valueOf(resultSet.getString("tier").toUpperCase()))
                .selectedState(resultSet.getString("selected_state"))
                .build();
    }
}

package com.youragent.dao;

import com.youragent.dao.DaoUtils.DaoUtils;
import com.youragent.dao.DaoUtils.SqlQueryConstants;
import com.youragent.dao.mapper.AgentMapper;
import com.youragent.model.Agent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
public class AgentDaoImpl implements Dao<Agent> {

    private static final String firstName = "first_name";
    private static final String lastName = "last_name";
    private static final String phoneNumber = "phone_number";
    private static final String email = "email";
    private static final String hasAccess = "has_access";
    private static final String tier = "tier";
    private static final String selectedState = "selected_state";
    private static final String selectedCounties = "selected_counties";
    private static final String clientIds = "client_ids";
    private static final String timeInserted = "time_inserted";

    private final JdbcTemplate jdbcTemplate;

    private final AgentMapper agentMapper;

    private final Timestamp timestamp;

    public AgentDaoImpl(@NonNull final JdbcTemplate jdbcTemplate,
                        @NonNull final AgentMapper agentMapper,
                        @NonNull final Timestamp timestamp){
        this.jdbcTemplate = jdbcTemplate;
        this.agentMapper = agentMapper;
        this.timestamp = timestamp;
    }

    @Override
    public Long save(@NonNull final Agent agent) {

        log.info("Attempting to save {} into agent table in database", agent);

        final String sql = String.format(SqlQueryConstants.AGENT_SAVE_SQL_QUERY,
                                                          firstName,
                                                          lastName,
                                                          phoneNumber,
                                                          email,
                                                          hasAccess,
                                                          tier,
                                                          selectedState,
                                                          timeInserted
        );

        log.info("query: {}", sql);

        final Long insertedAgentId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                agent.getFirstName(),
                agent.getLastName(),
                agent.getPhoneNumber(),
                agent.getEmail(),
                agent.isHasAcces(),
                agent.getTier().toString().toLowerCase(),
                agent.getSelectedState(),
                timestamp
        );

        log.info("Successfully inserted agent with primary key id = {}", insertedAgentId);

        return insertedAgentId;
    }

    @Override
    public Optional<Agent> get(final long agentId) {

        log.info("Attempting to get agent with id = {} from database", agentId);

        Agent agent;

        try {
            log.info("query: {}, where ? = {}", SqlQueryConstants.AGENT_GET_SQL_QUERY, agentId);

            agent = Objects.requireNonNull(
                    jdbcTemplate.query(
                            SqlQueryConstants.AGENT_GET_SQL_QUERY,
                            new Object[]{agentId},
                            new int[]{Types.BIGINT},
                            (ResultSetExtractor<Agent>) agentMapper
                    )
            );

        } catch (DataAccessException e) {
            log.error("No agent found with id = {}", agentId);
            throw new RuntimeException("Agent not found", e);
        }

        return Optional.of(agent);
    }

    @Override
    public List<Agent> getAll() {
        return List.of();
    }

    @Override
    public void update(final long agentId, String column, Object arg) {
        log.info("Attempting to update agent with id = {}", agentId);

        final String constructedQuery = DaoUtils.updateQueryBuilder(SqlQueryConstants.AGENT_UPDATE_SQL_QUERY, column);

        log.info("query: {}", constructedQuery);

        Object[] args = new Object[]{arg, agentId};
        int[] argTypes = new int[]{DaoUtils.getSqlType(arg), DaoUtils.getSqlType(agentId)};

        try {
            jdbcTemplate.update(
                    constructedQuery,
                    args,
                    argTypes
            );
        } catch (Exception e) {
            log.info("Did not successfully update agent with id = {}", agentId);
            throw new RuntimeException("Did not update agent", e);
        }

        log.info("Successfully updated agent with id = {}", agentId);
    }

    @Override
    public void delete(@NonNull final long agentId) {

        log.info("Attempting to delete agent with id = {}", agentId);

        log.info("query: {}, where ? = {}", SqlQueryConstants.AGENT_DELETE_SQL_QUERY, agentId);

        int rowsUpdated = jdbcTemplate.update(SqlQueryConstants.AGENT_DELETE_SQL_QUERY, new Object[]{agentId}, new int[]{Types.BIGINT});

        if (rowsUpdated == 0) {
            log.error("No agent deleted with id: {}", agentId);
            throw new RuntimeException("Agent not deleted");
        }
    }

    public void printAgentTable() {

        String query = "SELECT * FROM agents, clients";
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(query);

        int columnCount = resultSet.getMetaData().getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
        }
        System.out.println();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getObject(i) + "\t");
            }
            System.out.println();
        }
    }
}

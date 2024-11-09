package com.youragent.dao.AgentDao;

import com.youragent.dao.Dao;
import com.youragent.dao.DaoUtils.DaoUtils;
import com.youragent.dao.DaoUtils.SqlQueryConstants;
import com.youragent.model.Agent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.youragent.dao.DaoUtils.AgentColumnConstants.*;

@Repository
@Slf4j
public class AgentDaoImpl implements Dao<Agent> {

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
    public Long save(@NonNull final Agent agent) throws DataAccessException {

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
    public Agent get(final long agentId) throws DataAccessException {

        log.info("Attempting to get agent with id = {} from database", agentId);

        Agent agent;

        try {
            log.info("query: {}, where ? = {}", SqlQueryConstants.AGENT_GET_SQL_QUERY, agentId);

            final Object[] args = new Object[]{agentId};
            final int[] argTypes = new int[]{Types.BIGINT};

            agent = Objects.requireNonNull(
                    jdbcTemplate.query(
                            SqlQueryConstants.AGENT_GET_SQL_QUERY,
                            new Object[]{agentId},
                            new int[]{Types.BIGINT},
                            (ResultSetExtractor<Agent>) agentMapper
                    ),
                    "Failed to retrieve agent. Agent must not be null."
            );

        } catch (DataAccessException | NullPointerException e) {
            log.error("No agent found with id = {}", agentId);
            throw new DataRetrievalFailureException("Agent not found", e);
        }

        return agent;
    }

    @Override
    public List<Agent> getAll() {
        return List.of();
    }

    @Override
    public void update(final long agentId, String column, Object arg) throws DataAccessException {
        log.info("Attempting to update agent with id = {}", agentId);

        final String constructedQuery = DaoUtils.updateQueryBuilder(SqlQueryConstants.AGENT_UPDATE_SQL_QUERY, column);

        log.info("query: {}", constructedQuery);

        final Object[] args = new Object[]{arg, agentId};
        final int[] argTypes = new int[]{DaoUtils.getSqlType(arg), DaoUtils.getSqlType(agentId)};

        try {
            jdbcTemplate.update(
                    constructedQuery,
                    args,
                    argTypes
            );
        } catch (DataAccessException e) {
            log.info("Did not update agent with id = {}, column = {}, arg = {}", agentId, column, arg);
            throw new DataRetrievalFailureException(
                    String.format("Did not update agent with id = %s, column = %s, arg = %s", agentId, column, arg),
                    e
            );
        }

        log.info("Successfully updated agent with id = {}", agentId);
    }

    @Override
    public void delete(final long agentId) throws DataAccessException {

        log.info("Attempting to delete agent with id = {}", agentId);

        log.info("query: {}, where ? = {}", SqlQueryConstants.AGENT_DELETE_SQL_QUERY, agentId);

        int rowsUpdated = jdbcTemplate.update(SqlQueryConstants.AGENT_DELETE_SQL_QUERY, new Object[]{agentId}, new int[]{Types.BIGINT});

        if (rowsUpdated == 0) {
            log.error("No agent deleted with id: {}", agentId);
            throw new DataSourceLookupFailureException("Agent not deleted");
        }
    }

    public Optional<Long> getNextAgentInCounty(@NonNull final String county, @NonNull final String state) {

        log.info("Attempting to get the next agent from {}, {} agent queue", county, state);

        Long agentId;

        try {
            log.info("query: {}, where county = {} and state = {}",
                    SqlQueryConstants.AGENT_GET_NEXT_AGENT_FROM_COUNTY_QUEUE_SQL_QUERY, county, state);

            final Object[] args = new Object[]{county, state};
            final int[] argTypes = new int[]{Types.VARCHAR, Types.VARCHAR};

            agentId = Objects.requireNonNull(
                    jdbcTemplate.queryForObject(
                            SqlQueryConstants.AGENT_GET_NEXT_AGENT_FROM_COUNTY_QUEUE_SQL_QUERY,
                            args,
                            argTypes,
                            Long.class
                    )
            );

        } catch (DataAccessException | NullPointerException e) {
            log.error("No agents found in county = {}, state = {}", county, state);
            throw new DataRetrievalFailureException("Agents not found", e);
        }

        return Optional.of(agentId);
    }

    public Timestamp getCurrentTimestamp() {
        return timestamp;
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

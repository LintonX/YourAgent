package com.youragent.dao.authdao;

import com.youragent.dao.Dao;
import com.youragent.dao.DaoUtils.DaoUtils;
import com.youragent.dao.DaoUtils.SqlQueryConstants;
import com.youragent.model.dto.AgentCredentials;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Objects;

import static com.youragent.dao.DaoUtils.AgentColumnConstants.*;

@Repository
@Slf4j
public class AuthDaoImpl implements Dao<AgentCredentials> {

    private final JdbcTemplate jdbcTemplate;

    private final AuthMapper authMapper;

    public AuthDaoImpl(@NonNull final JdbcTemplate jdbcTemplate,
                       @NonNull final AuthMapper authMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.authMapper = authMapper;
    }


    @Override
    public Long save(@NonNull final AgentCredentials agentCredentials) throws DataAccessException {

        log.info("Attempting to save agent credentials into agentcredentials table in database");

        final String sql = String.format(SqlQueryConstants.AGENT_CREDENTIALS_SAVE_CREDENTIALS_SQL_QUERY,
                email,
                pass
        );

        log.info("query: {}", sql);

        final Long insertedCredentialId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                agentCredentials.getEmail(),
                agentCredentials.getPassword()
        );

        log.info("Successfully inserted agent creds with primary key id = {}", insertedCredentialId);

        return insertedCredentialId;
    }

    public AgentCredentials get(@NonNull final String email) throws DataAccessException {

        log.info("Attempting to get agent credentials from agentcredentials table in database");

        final String sql = String.format(SqlQueryConstants.AGENT_CREDENTIALS_GET_AGENT_CREDENTIALS_SQL_QUERY);

        log.info("query: {}, where ? = {}", SqlQueryConstants.AGENT_CREDENTIALS_GET_AGENT_CREDENTIALS_SQL_QUERY, email);

        AgentCredentials agentCredentials;

        try {

            agentCredentials = Objects.requireNonNull(
                    jdbcTemplate.queryForObject(
                            SqlQueryConstants.AGENT_CREDENTIALS_GET_AGENT_CREDENTIALS_SQL_QUERY,
                            new Object[]{email},
                            new int[]{Types.VARCHAR},
                            authMapper
                    ),
                    "Failed to retrieve id and password. Either must not be null"
            );
        } catch (DataAccessException e) {
            log.error("No credentials found with email: {}", email);
            throw new DataRetrievalFailureException("Credentials not found", e);
        }

        return agentCredentials;
    }


    @Override
    public AgentCredentials get(long id) throws DataAccessException {
        return null;
    }

    @Override
    public List<AgentCredentials> getAll() throws DataAccessException {
        return List.of();
    }

    @Override
    public void update(long credentialId, String column, Object arg) throws DataAccessException {
        log.info("Attempting to update agent credential with id = {}", credentialId);

        final String constructedQuery = DaoUtils.updateQueryBuilder(SqlQueryConstants.AGENT_CREDENTIALS_UPDATE_SQL_QUERY, column);

        log.info("query: {}", constructedQuery);

        final Object[] args = new Object[]{arg, credentialId};
        final int[] argTypes = new int[]{DaoUtils.getSqlType(arg), DaoUtils.getSqlType(credentialId)};

        try {
            jdbcTemplate.update(
                    constructedQuery,
                    args,
                    argTypes
            );
        } catch (DataAccessException e) {
            log.info("Did not update agent credential with id = {}, column = {}, arg = {}", credentialId, column, arg);
            throw new DataRetrievalFailureException(
                    String.format("Did not update agent credential with id = %s, column = %s, arg = %s", credentialId, column, arg),
                    e
            );
        }

        log.info("Successfully updated agent credential with id = {}", credentialId);
    }

    @Override
    public void delete(long id) throws DataAccessException {

    }
}

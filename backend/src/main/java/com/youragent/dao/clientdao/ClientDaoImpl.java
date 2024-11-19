package com.youragent.dao.clientdao;

import com.youragent.dao.Dao;
import com.youragent.dao.DaoUtils.DaoUtils;
import com.youragent.dao.DaoUtils.SqlQueryConstants;
import com.youragent.model.Client;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;

import static com.youragent.dao.DaoUtils.ClientColumnConstants.*;

@Repository
@Slf4j
public class ClientDaoImpl implements Dao<Client> {

    private final JdbcTemplate jdbcTemplate;

    private final ClientMapper clientMapper;

    public ClientDaoImpl(@NonNull final JdbcTemplate jdbcTemplate,
                         @NonNull final ClientMapper clientMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.clientMapper = clientMapper;
    }

    @Override
    public Long save(@NonNull final Client client) throws DataAccessException {

        log.info("Attempting to save {} into client table in database", client);

        final String sql = String.format(SqlQueryConstants.CLIENT_SAVE_SQL_QUERY,
                                                           firstName,
                                                           lastName,
                                                           email,
                                                           phoneNumber,
                                                           searchedState,
                                                           searchedCounty,
                                                           searchedPlace,
                                                            timeInserted
        );

        log.info("query: {}", sql);

        Long insertedClientId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getSearchedState(),
                DaoUtils.escapeSingleQuotes(client.getSearchedCounty()),
                DaoUtils.escapeSingleQuotes(client.getSearchedPlace()),
                DaoUtils.getCurrentTimestamp()
        );

        log.info("Successfully inserted client with primary key id = {}", insertedClientId);

        return insertedClientId;
    }

    @Override
    public Client get(final long clientId) throws DataAccessException {

        log.info("Attempting to get {} from client table in database", clientId);

        final String sql = "SELECT * FROM clients WHERE clients.client_id = ?";

        Client client;

        try {
            log.info("query: {}, where ? = {}", SqlQueryConstants.CLIENT_GET_SQL_QUERY, clientId);

            client = Objects.requireNonNull(
                    jdbcTemplate.queryForObject(
                            SqlQueryConstants.CLIENT_GET_SQL_QUERY,
                            new Object[]{clientId},
                            new int[]{Types.BIGINT},
                            clientMapper),
                    "Failed to retrieve client. Client must not be null"
            );

        } catch (DataAccessException e) {
            log.error("No client found with id: {}", clientId);
            throw new DataRetrievalFailureException("Client not found", e);
        }

        return client;
    }

    @Override
    public List<Client> getAll() {
        return List.of();
    }

    /*
        clientId = id of client to update
        column = column to update
        arg = change column value to this arg
     */
    @Override
    public void update(final long clientId, String column, Object arg) throws DataAccessException {

        log.info("Attempting to update client: {}", clientId);

        String constructedQuery = DaoUtils.updateQueryBuilder(SqlQueryConstants.CLIENT_UPDATE_SQL_QUERY, column);

        log.info("query: {}", constructedQuery);

        Object[] args = new Object[]{arg, clientId};
        int[] argTypes = new int[]{DaoUtils.getSqlType(arg), DaoUtils.getSqlType(clientId)};

        try {
            jdbcTemplate.update(
                    constructedQuery,
                    args,
                    argTypes
            );
        } catch (Exception e) {
            log.info("Did not update client with id = {}, column = {}, arg = {}", clientId, column, arg);
            throw new DataRetrievalFailureException(
                    String.format("Did not update client with id = %s, column = %s, arg = %s", clientId, column, arg),
                    e
            );
        }

        log.info("Successfully updated client with id = {}", clientId);
    }

    @Override
    public void delete(final long id) {

    }
}

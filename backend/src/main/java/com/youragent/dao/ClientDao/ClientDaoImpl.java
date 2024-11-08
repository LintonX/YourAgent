package com.youragent.dao;

import com.youragent.dao.DaoUtils.DaoUtils;
import com.youragent.dao.DaoUtils.SqlQueryConstants;
import com.youragent.dao.mapper.ClientMapper;
import com.youragent.model.Client;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;

@Repository
@Slf4j
public class ClientDaoImpl implements Dao<Client> {

    private static final String assignedAgentId = "id_of_agent";
    private static final String firstName = "first_name";
    private static final String lastName = "last_name";
    private static final String phoneNumber = "phone_number";
    private static final String email = "email";
    private static final String searchedState = "searched_state";
    private static final String searchedPlace = "searched_place";
    private static final String searchedCounty = "searched_county";

    private final JdbcTemplate jdbcTemplate;

    private final ClientMapper clientMapper;

    public ClientDaoImpl(@NonNull final JdbcTemplate jdbcTemplate,
                         @NonNull final ClientMapper clientMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.clientMapper = clientMapper;
    }

    @Override
    public Long save(@NonNull final Client client) {

        log.info("Attempting to save {} into client table in database", client);

        final String sql = String.format(SqlQueryConstants.CLIENT_SAVE_SQL_QUERY,
                                                           firstName,
                                                           lastName,
                                                           email,
                                                           phoneNumber,
                                                           searchedState,
                                                           searchedCounty,
                                                           searchedPlace
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
                client.getSearchedCounty(),
                client.getSearchedPlace()
        );

        log.info("Successfully inserted client with primary key id = {}", insertedClientId);

        return insertedClientId;
    }

    @Override
    public Optional<Client> get(final long clientId) {

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
                            clientMapper)
            );

        } catch (DataAccessException e) {
            log.error("No client found with id: {}", clientId);
            throw new RuntimeException("Client not found", e);
        }

        return Optional.of(client);
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
    public void update(final long clientId, String column, Object arg) {

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
            log.info("Did not successfully update client with id = {}", clientId);
            throw new RuntimeException("Did not update client", e);
        }

        log.info("Successfully updated client with id = {}", clientId);
    }

    @Override
    public void delete(@NonNull final long id) {

    }
}

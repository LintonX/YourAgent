package com.youragent.dao.mapper;

import com.youragent.model.Client;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClientMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Client.builder()
                .id(resultSet.getLong("client_id"))
                .assignedAgentId(resultSet.getLong("id_of_agent"))
                .firstName(resultSet.getString("client_first_name"))
                .lastName(resultSet.getString("client_last_name"))
                .email(resultSet.getString("client_email"))
                .phoneNumber(resultSet.getString("client_phone_number"))
                .searchedPlace(resultSet.getString("searched_place"))
                .searchedCounty(resultSet.getString("searched_county"))
                .searchedState(resultSet.getString("searched_state"))
                .build();
    }
}

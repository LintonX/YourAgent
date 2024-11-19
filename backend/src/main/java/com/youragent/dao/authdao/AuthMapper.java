package com.youragent.dao.authdao;

import com.youragent.model.dto.AgentCredentials;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthMapper implements RowMapper<AgentCredentials> {

    @Override
    public AgentCredentials mapRow(ResultSet rs, int rowNum) throws SQLException {

        final Long credentialId = rs.getLong("cred_id");
        final String credentialPassword = rs.getString("pass");
        final Long idOfAssociatedAgent = rs.getLong("id_of_agent");

        return AgentCredentials.builder()
                .credId(credentialId)
                .password(credentialPassword)
                .idOfAssociatedAgent(idOfAssociatedAgent).build();
    }
}

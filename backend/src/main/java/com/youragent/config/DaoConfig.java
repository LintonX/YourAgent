package com.youragent.config;

import com.youragent.dao.agentdao.AgentMapper;
import com.youragent.dao.authdao.AuthDaoImpl;
import com.youragent.dao.authdao.AuthMapper;
import com.youragent.dao.clientdao.ClientMapper;
import com.youragent.dao.agentdao.AgentDaoImpl;
import com.youragent.dao.clientdao.ClientDaoImpl;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DaoConfig {

    @Bean
    public AgentDaoImpl agentDao(@NonNull final JdbcTemplate jdbcTemplate,
                                 @NonNull final AgentMapper agentMapper) {
        return new AgentDaoImpl(jdbcTemplate, agentMapper);
    }

    @Bean
    public ClientDaoImpl clientDao(@NonNull final JdbcTemplate jdbcTemplate,
                                   @NonNull final ClientMapper clientMapper) {
        return new ClientDaoImpl(jdbcTemplate, clientMapper);
    }

    @Bean
    public AuthDaoImpl authDao(@NonNull final JdbcTemplate jdbcTemplate,
                               @NonNull final AuthMapper authMapper) {
        return new AuthDaoImpl(jdbcTemplate, authMapper);
    }

    @Bean
    public AgentMapper agentMapper(@NonNull final ClientMapper clientMapper) {
        return new AgentMapper(clientMapper);
    }

    @Bean
    public ClientMapper clientMapper() {
        return new ClientMapper();
    }

    @Bean
    public AuthMapper authMapper() { return new AuthMapper(); };

    @Bean
    public JdbcTemplate jdbcTemplate(@NonNull final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

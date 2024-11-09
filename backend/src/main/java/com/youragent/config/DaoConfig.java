package com.youragent.config;

import com.youragent.dao.AgentDao.AgentMapper;
import com.youragent.dao.ClientDao.ClientMapper;
import com.youragent.dao.AgentDao.AgentDaoImpl;
import com.youragent.dao.ClientDao.ClientDaoImpl;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;

@Configuration
public class DaoConfig {

    @Bean
    public AgentDaoImpl agentDao(@NonNull final JdbcTemplate jdbcTemplate,
                               @NonNull final AgentMapper agentMapper,
                               @NonNull final Timestamp timestamp) {
        return new AgentDaoImpl(jdbcTemplate, agentMapper, timestamp);
    }

    @Bean
    public ClientDaoImpl clientDao(@NonNull final JdbcTemplate jdbcTemplate,
                                 @NonNull final ClientMapper clientMapper) {
        return new ClientDaoImpl(jdbcTemplate, clientMapper);
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
    public JdbcTemplate jdbcTemplate(@NonNull final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Timestamp timestamp() {
        return Timestamp.from(Instant.now().atZone(ZoneOffset.UTC).toInstant());
    }
}

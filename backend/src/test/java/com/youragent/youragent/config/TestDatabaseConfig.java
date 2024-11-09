package com.youragent.youragent.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Testcontainers
public class TestDatabaseConfig {

//    @Container
//    @ServiceConnection
//    private final PostgreSQLContainer<?> postgreSQLContainer =
//            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.0"))
//                    .withInitScript("init.sql")
//                    .withCreateContainerCmdModifier(cmd -> cmd.withName("testcontainer-AgentDaoTests"))
//                    .withDatabaseName("agent_dao_tests")
//                    .withUsername("postgres")
//                    .withPassword("postgres");
//
//    @Bean
//    public PostgreSQLContainer<?> postgreSQLContainer() {
//        return postgreSQLContainer;
//    }

//    @Bean
//    public HikariDataSource dataSource(@NonNull final PostgreSQLContainer<?> postgreSQLContainer) {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
//        hikariConfig.setUsername(postgreSQLContainer.getUsername());
//        hikariConfig.setPassword(postgreSQLContainer.getPassword());
//        return new HikariDataSource(hikariConfig);
//    }
}

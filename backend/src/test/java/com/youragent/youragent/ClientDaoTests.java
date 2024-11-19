package com.youragent.youragent;

import com.youragent.dao.clientdao.ClientDaoImpl;
import com.youragent.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@Slf4j
public class ClientDaoTests {

    @Autowired
    private ClientDaoImpl clientDao;

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.0"))
                    .withInitScript("init.sql")
                    .withCreateContainerCmdModifier(cmd -> cmd.withName("testcontainer-AgentDaoTests"))
                    .withDatabaseName("client-dao-tests-db")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @Test
    public void testPostgresConnection() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    public void save_validClient_shouldReturnValidClientId() {
        final Client client = getTestClient2();

        Long savedClientId = clientDao.save(client);

        assertThat(savedClientId).isNotNull();
        assertThat(savedClientId).isInstanceOf(Long.class);
        assertThat(savedClientId).isGreaterThan(0L);
    }

    @Test
    public void get_validClient_shouldReturnValidClient() {
        Client fetchedClient = clientDao.get(2);

        assertThat(fetchedClient.getId()).isEqualTo(2);
        assertThat(fetchedClient.getFirstName()).isEqualTo("Michael");
        assertThat(fetchedClient.getLastName()).isEqualTo("Smith");
    }

    @Test void get_nonexistentClient_shouldThrowRuntimeError() {
        assertThrows(RuntimeException.class, () -> clientDao.get(1000L));
    }

    // Should change id of agent from 2L to 1L
    @Test void update_validClient_shouldUpdateClientWithProvidedAgentId() {
        Client client = getTestClient1();

        clientDao.update(client.getId(), "id_of_agent", 1L);

        Client updatedClient = clientDao.get(client.getId());

        assertThat(updatedClient.getAssignedAgentId()).isEqualTo(1L);
    }

    private Client getTestClient1() {
        return Client.builder()
                .id(2L)
                .firstName("Michael")
                .lastName("Smith")
                .email("michael.smith@example.com")
                .phoneNumber("410-555-0145")
                .searchedCounty("Anne Arundel")
                .searchedState("MD")
                .searchedPlace("Annapolis")
                .timeInserted(Timestamp.valueOf("2024-11-04 23:12:31.515611342"))
                .build();
    }

    private Client getTestClient2() {
        return Client.builder()
                .firstName("Shirley")
                .lastName("Apples")
                .email("shirley.apples@example.com")
                .phoneNumber("301-245-0225")
                .searchedCounty("Montgomery")
                .searchedState("MD")
                .searchedPlace("Gaithersburg")
                .timeInserted(Timestamp.valueOf("2024-11-04 23:13:31.515611342"))
                .build();
    }
}

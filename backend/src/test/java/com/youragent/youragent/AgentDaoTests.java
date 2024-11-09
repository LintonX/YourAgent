package com.youragent.youragent;

import com.youragent.dao.AgentDao.AgentDaoImpl;
import com.youragent.model.Agent;
import com.youragent.model.AgentTier;
import com.youragent.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@Slf4j
public class AgentDaoTests {

    @Autowired
    private AgentDaoImpl agentDao;

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.0"))
                    .withInitScript("init.sql")
                    .withCreateContainerCmdModifier(cmd -> cmd.withName("testcontainer-AgentDaoTests"))
                    .withDatabaseName("agent_dao_tests")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @BeforeEach
    void setUp() {
        postgreSQLContainer.start();
    }

    @Test
    public void testPostgresConnection() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    public void save_validAgent_shouldReturnAssignedId() throws SQLException {
        final Agent agent = generateTestAgent2();
        final Long savedAgentId = agentDao.save(agent);

        assertThat(savedAgentId).isNotNull();
        assertThat(savedAgentId).isInstanceOf(Long.class);
        assertThat(savedAgentId).isGreaterThan(0L);
    }

    @Test
    public void get_validAgent_shouldReturnAgentWithAssociatedClients() {
        final Agent fetchedAgent = agentDao.get(1L);

        Client client = generateTestClient2();

        assertThat(fetchedAgent.getFirstName()).isEqualTo("Alice");
        assertThat(fetchedAgent.getLastName()).isEqualTo("Taylor");
        assertThat(fetchedAgent.isHasAcces()).isTrue();
        assertThat(fetchedAgent.getClients().size()).isEqualTo(3);
        assertThat(fetchedAgent.getClients()).contains(client);
    }

    @Test
    void update_validAgent_shouldUpdateAgentPhoneNumberWithProvidedPhoneNumber() {
        Agent agent = generateTestAgent2();

        agentDao.update(agent.getId(), "phone_number", "555-555-5555");

        Agent updatedAgent = agentDao.get(agent.getId());

        assertThat(updatedAgent.getPhoneNumber()).isEqualTo("555-555-5555");
    }

    @Test
    public void delete_validAgent_shouldDeleteAgentThatMatchesGivenId() {
        agentDao.delete(1L);

        assertThrows(DataAccessException.class, () -> agentDao.get(1L));
        assertThrows(RuntimeException.class, () -> agentDao.delete(1L));
    }

    @Test
    public void get_nextAgentId_shouldReturnNextAgentIdFromCountyQueueWithClosestTimestamp() {
        final Optional<Long> idOfNextAgent1 =
                agentDao.getNextAgentInCounty("Montgomery", "MD");
        final Optional<Long> idOfNextAgent2 =
                agentDao.getNextAgentInCounty("Prince Georges", "MD");
        final Optional<Long> idOfNextAgent3 =
                agentDao.getNextAgentInCounty("Howard", "MD");

        assertThat(idOfNextAgent1.isPresent()).isTrue();
        assertThat(idOfNextAgent2.isPresent()).isTrue();
        assertThat(idOfNextAgent3.isPresent()).isTrue();

        // agent with id of 1 should be returned here
        assertThat(idOfNextAgent1.get()).isEqualTo(1L);

        // agent with id of 1 should be returned here even though two agents exist in county,
        // agent 1 should be first in county queue based on timestamp
        assertThat(idOfNextAgent2.get()).isEqualTo(1L);

        // agent with id of 2 should be returned here
        assertThat(idOfNextAgent3.get()).isEqualTo(2L);
    }

    @Test
    public void get_countyWithNoAgents_shouldThrowExceptionGettingNextAgentIdWithClosestTimestamp() {
        assertThrows(DataAccessException.class,
                () -> agentDao.getNextAgentInCounty("Frederick", "MD"));
    }

    public Agent generateTestAgent1() {
        return Agent.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Taylor")
                .email("alice.taylor@example.com")
                .phoneNumber("301-555-0123")
                .hasAcces(true)
                .tier(AgentTier.BASIC)
                .selectedState("MD")
                .build();
    }

    public Agent generateTestAgent2() {
        return Agent.builder()
                .id(2L)
                .firstName("Kevin")
                .lastName("Johnson")
                .email("kevin.johnson@example.com")
                .phoneNumber("301-432-0246")
                .hasAcces(true)
                .tier(AgentTier.BASIC)
                .selectedState("MD")
                .build();
    }

    public Client generateTestClient1() {
        return Client.builder()
                .id(4L)
                .assignedAgentId(1L)
                .firstName("David")
                .lastName("Brown")
                .email("david.brown@example.com")
                .phoneNumber("443-555-0123")
                .searchedState("MD")
                .searchedCounty("Baltimore")
                .searchedPlace("Baltimore")
                .build();
    }

    public Client generateTestClient2() {
        return Client.builder()
                .id(1L)
                .assignedAgentId(1L)
                .firstName("Emily")
                .lastName("Johnson")
                .email("emily.johnson@example.com")
                .phoneNumber("301-555-0171")
                .searchedState("MD")
                .searchedCounty("Montgomery")
                .searchedPlace("Rockville")
                .build();
    }
}

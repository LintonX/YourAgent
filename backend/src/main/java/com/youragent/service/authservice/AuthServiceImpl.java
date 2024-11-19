package com.youragent.service.authservice;

import com.youragent.dao.authdao.AuthDaoImpl;
import com.youragent.model.Agent;
import com.youragent.model.dto.AgentCredentials;
import com.youragent.service.agentservice.AgentServiceImpl;
import com.youragent.service.redisservice.RedisService;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl {

    private final AuthDaoImpl authDao;

    private final AgentServiceImpl agentService;

    private final RedisService<String, Agent> redisService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(@NonNull final AuthDaoImpl authDao,
                           @NonNull final AgentServiceImpl agentService,
                           @NonNull final RedisService<String, Agent> redisService,
                           @NonNull final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authDao = authDao;
        this.agentService = agentService;
        this.redisService = redisService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Long createAgentAccount(@NonNull final AgentCredentials agentCredentials) {

        log.info("Attempting to create a new agent account");

        final String encryptedPassword = bCryptPasswordEncoder.encode(agentCredentials.getPassword());

        log.info("plaintext password = {}, encrypted password = {}", agentCredentials.getPassword(), encryptedPassword);

        final AgentCredentials encryptedAgent = AgentCredentials.builder()
                .email(agentCredentials.getEmail())
                .password(encryptedPassword)
                .build();

        return authDao.save(encryptedAgent);
    }

    public Agent verifyAgentCredentialsAndSetContext(@NonNull final AgentCredentials agentCredentials,
                                                     @NonNull HttpSession session) throws LoginException {

        log.info("Attempting to verify agent credentials");

        final AgentCredentials fetchedAgentCredentials = authDao.get(agentCredentials.getEmail());
        final Long credentialId = fetchedAgentCredentials.getCredId();
        final String encryptedPassword = fetchedAgentCredentials.getPassword();

        boolean doPasswordsMatch = bCryptPasswordEncoder.matches(agentCredentials.getPassword(), encryptedPassword);

        // passwords dont match, notify caller with exception
        if (!doPasswordsMatch) {
            throw new LoginException("Password is incorrect.");
        }

        Agent agent = null;

        // paswords match, get agent context
        // if agent hasn't paid, ask for county details and payment (we only update agent context with
        // details once agent has completed stripe process)
        if (fetchedAgentCredentials.getIdOfAssociatedAgent() != 0) {
            agent = agentService.getAgentContext(fetchedAgentCredentials.getIdOfAssociatedAgent());
        } else {
            agent = Agent.builder().id(credentialId).email(agentCredentials.getEmail()).build();
        }

        return agent;
    }

    public void mapSavedAgentToAgentCredentials(@NonNull final Agent agent,
                                                   @NonNull Long credentialIdOfAgent) {
        // save agent
        // get id of saved agent
        Long agentId = agentService.saveAgent(agent);

        // update credential id to be that of saved agent id
        authDao.update(credentialIdOfAgent, "id_of_agent", agentId);
    }

//    public HttpHeaders establishSession(@NonNull final Agent agent,
//                                        @NonNull final HttpSession session) {
//
//        log.info("Attempting to establish a user session for {}. Browser session = {}", agent.getEmail(), session.getId());
//
//        final Agent sessionAgent = redisService.getValue(session.getId());
//
//        if (session.getId() != null && sessionAgent != null) {
//            log.info("Found an existing session with id = {}", sessionId);
//            log.info("Session has been verified");
//            return null;
//        }
//
//        final String newSessionId = UUID.randomUUID().toString();
//
//        log.info("Attempting to set session with id = {}", newSessionId);
//
//        redisService.setValue(credentialId, newSessionId, Duration.ofDays(7L));
//
//        ResponseCookie sessionCookie = ResponseCookie.from("AGENT_SESSION_ID", newSessionId)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(604800)
//                .build();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.SET_COOKIE, sessionCookie.toString());
//
//        return headers;
//    }
}

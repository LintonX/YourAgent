package com.youragent.controller;

import com.youragent.model.Agent;
import com.youragent.model.dto.AgentCredentials;
import com.youragent.service.authservice.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
@Slf4j
@CrossOrigin(origins = "${frontend.address}")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(@NonNull final AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/auth/createAgentAccount")
    public ResponseEntity<Long> createAgentAccount(@RequestBody @NonNull AgentCredentials agentCredentials) {
        log.info("POST: createAgentAccount, email = {},", agentCredentials.getEmail());

        Long agentCredentialId = authService.createAgentAccount(agentCredentials);

        if (agentCredentialId != null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(agentCredentialId);
        }

        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
    }

    @PostMapping(path = "/auth/signin")
    public ResponseEntity<Agent> agentSignIn(@RequestBody @NonNull AgentCredentials agentCredentials,
                                             HttpSession session) {
        log.info("POST: agentSignIn, email = {}, credId = {}", agentCredentials.getEmail(), agentCredentials.getCredId());

        try {
            final Agent agent = authService.verifyAgentCredentialsAndSetContext(agentCredentials, session);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(agent);
//            authService.establishSession(agent, session);
        } catch (LoginException | DataAccessException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
        }
    }

    @PostMapping(path = "/auth/signout")
    public ResponseEntity<?> agentSignOut(HttpSession session) {
        return null;
    }
}

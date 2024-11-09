package com.youragent.controller;

import com.youragent.dao.AgentDao.AgentDaoImpl;
import com.youragent.model.Agent;
import com.youragent.service.agentservice.AgentServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j
@CrossOrigin(origins = "${frontend.address}")
public class AgentController {

    private final AgentServiceImpl agentService;

    public AgentController(@NonNull AgentServiceImpl agentService) {
        this.agentService = agentService;
    }

    @GetMapping(path = "/getAgentContext")
    public ResponseEntity<Agent> getAgentContext(@RequestParam Long agentId) {
        log.info("GET: getAgentContext agentId={},", agentId);

        Agent agent;

        try {
            agent = agentService.getAgentContext(agentId);
        } catch (Exception e) {
            log.info(e.toString());
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(agent);
    }
}

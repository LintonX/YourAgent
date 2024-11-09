package com.youragent.service.agentservice;

import com.youragent.dao.AgentDao.AgentDaoImpl;
import com.youragent.model.Agent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService{

    private final AgentDaoImpl agentDao;

    public AgentServiceImpl(@NonNull AgentDaoImpl agentDao) {
        this.agentDao = agentDao;
    }

    public Agent getAgentContext(long agentId) {
        return agentDao.get(agentId);
    }

}

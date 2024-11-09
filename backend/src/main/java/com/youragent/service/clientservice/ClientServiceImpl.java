package com.youragent.service.clientservice;

import com.youragent.dao.AgentDao.AgentDaoImpl;
import com.youragent.dao.ClientDao.ClientDaoImpl;
import com.youragent.dao.DaoUtils.AgentColumnConstants;
import com.youragent.dao.DaoUtils.ClientColumnConstants;
import com.youragent.model.Client;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final AgentDaoImpl agentDao;

    private final ClientDaoImpl clientDao;

    public ClientServiceImpl(@NonNull AgentDaoImpl agentDao,
                             @NonNull ClientDaoImpl clientDao) {
        this.agentDao = agentDao;
        this.clientDao = clientDao;
    }

    @Override
    public boolean saveAndAssignClient(Client client) {

        log.info("Attempting to save client and assign an agent to them...");

        try {
            final Long clientId = clientDao.save(client);

            // get next agent in county provided by client object, retrieve the agents id
            final Long idOfAgent = getNextAgentInCounty(client.getSearchedState(), client.getSearchedCounty());

            // assign that agent id to clients id_of_agent column
            clientDao.update(clientId, ClientColumnConstants.assignedIdOfAgent, idOfAgent);

            // update that agents timestamp via id which essentially moves agent to back of county queue
            agentDao.update(idOfAgent, AgentColumnConstants.timeInserted, agentDao.getCurrentTimestamp());

        } catch (DataAccessException e) {
            log.error("Error saving and assigning agent to client", e);
            return false;
        }
        return true;
    }

    @Override
    public Long getNextAgentInCounty(String state, String county) {
        Optional<Long> idOfNextAgent = agentDao.getNextAgentInCounty(state, county);
        return idOfNextAgent.orElse(idOfNextAgent.orElseThrow());
    }
}

package com.youragent.service.clientservice;

import com.youragent.model.Client;

public interface ClientService {

    boolean saveAndAssignClient(Client client);

    Long getNextAgentInCounty(String state, String county);
}

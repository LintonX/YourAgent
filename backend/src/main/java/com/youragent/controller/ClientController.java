package com.youragent.controller;

import com.youragent.model.Client;
import com.youragent.service.clientservice.ClientService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "${frontend.address}")
public class ClientController {

    private final ClientService clientService;

    public ClientController(@NonNull final ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(path = "/processClientSubmit")
    public void processClientSubmit(@RequestBody @NonNull Client client) {
        log.info(client.toString());

        boolean wasClientInserted = clientService.saveAndAssignClient(client);
    }

}

package com.youragent.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Agent {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean hasAcces;

    private AgentTier tier;

    private String selectedState;

    private List<String> selectedCounties;

    private List<Client> clients;
}

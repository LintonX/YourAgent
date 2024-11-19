package com.youragent.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agent {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private boolean hasAccess;

    private AgentTier tier;

    private String selectedState;

    private List<String> selectedCounties;

    private List<Client> clients;
}

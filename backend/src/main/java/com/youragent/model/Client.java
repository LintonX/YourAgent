package com.youragent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @JsonProperty("client_id")
    private long id;

    @JsonProperty("client_id_of_agent")
    private long assignedAgentId;

    @JsonProperty("client_first_name")
    private String firstName;

    @JsonProperty("client_last_name")
    private String lastName;

    @JsonProperty("client_email")
    private String email;

    @JsonProperty("client_phone_number")
    private String phoneNumber;

    @JsonProperty("client_searched_county")
    private String searchedCounty;

    @JsonProperty("client_searched_state")
    private String searchedState;

    @JsonProperty("client_searched_place")
    private String searchedPlace;
}

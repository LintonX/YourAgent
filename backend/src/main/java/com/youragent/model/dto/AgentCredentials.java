package com.youragent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentCredentials {

    @JsonProperty(value = "cred_id", required = false)
    private Long credId;

    @JsonProperty(value = "email", required = true)
    private String email;

    @JsonProperty(value = "pass", required = true)
    private String password;

    @JsonProperty(value = "id_of_agent", required = false)
    private Long idOfAssociatedAgent;
}

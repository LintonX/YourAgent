package com.youragent.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String searchedPlace;

    private String searchedState;

    private Long assignedAgent;
}

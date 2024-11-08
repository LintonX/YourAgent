package com.youragent.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Boolean hasAcces;

    private String tier;
}

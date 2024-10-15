package com.youragent.model.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Data
public class PlaceState implements Serializable {

    private final String place;

    private final String state;

}

package com.youragent.service.restservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestServiceImpl<T> implements RestService<T>{

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public RestServiceImpl(@NonNull RestTemplate restTemplate,
                           @NonNull ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public T getRequest(@NonNull String uri, @NonNull Class<T> responseType) {
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(uri, responseType);
        return responseEntity.getBody();
    }
}

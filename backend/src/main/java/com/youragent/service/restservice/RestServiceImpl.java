package com.youragent.service.restservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RestServiceImpl<T> implements RestService<T>{

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public RestServiceImpl(@NonNull RestTemplate restTemplate,
                           @NonNull ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public T getRequest(@NonNull String uri, @NonNull Class<T> responseType) {
        log.info(uri);
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(uri, responseType);
        return responseEntity.getBody();
    }

    @Override
    public T postRequest(@NonNull String uri,
                         @NonNull HttpHeaders httpHeaders,
                         @NonNull String body,
                         @NonNull Class<T> responseType) {
        log.info(uri);

        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);

        log.info(httpEntity.toString());

        ResponseEntity<T> responseEntity =
                restTemplate.exchange(uri, HttpMethod.POST, httpEntity, responseType);

        return responseEntity.getBody();
    }
}

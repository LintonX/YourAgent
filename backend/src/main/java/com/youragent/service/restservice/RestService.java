package com.youragent.service.restservice;

import org.springframework.http.HttpHeaders;

public interface RestService<T> {

    T getRequest(String uri, Class<T> responseType);

    T postRequest(String uri, HttpHeaders headers, String body, Class<T> responseType);
}

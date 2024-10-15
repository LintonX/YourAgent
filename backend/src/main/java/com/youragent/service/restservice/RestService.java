package com.youragent.service.restservice;

public interface RestService<T> {

    public T getRequest(String uri, Class<T> responseType);
}

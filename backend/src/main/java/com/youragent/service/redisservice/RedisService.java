package com.youragent.service.redisservice;

import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RedisService<K, V> {

    public CompletableFuture<V> getValue(@NonNull final K key);

    public CompletableFuture<Void> setValue(@NonNull final K key, @NonNull final V value);
}

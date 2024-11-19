package com.youragent.service.redisservice;

import lombok.NonNull;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public interface RedisService<K, V> {

    V getValue(@NonNull final K key);

    void setValue(@NonNull final K key, @NonNull final V value);

    void setValue(@NonNull final K key, @NonNull final V value, @NonNull final Duration timeout);
}

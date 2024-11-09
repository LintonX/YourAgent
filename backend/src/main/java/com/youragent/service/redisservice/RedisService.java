package com.youragent.service.redisservice;

import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface RedisService<K, V> {

    CompletableFuture<V> getValue(@NonNull final K key);

    CompletableFuture<Void> setValue(@NonNull final K key, @NonNull final V value);
}

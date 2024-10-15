package com.youragent.service.redisservice;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RedisServiceImpl<K, V> implements RedisService<K, V>{

    private final RedisTemplate<K, V> redisTemplate;

    public RedisServiceImpl(@NonNull RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CompletableFuture<Void> setValue(@NonNull final K key, @NonNull final V value) {
        log.info("Storing {} : {} in cache.", key, value);
        return CompletableFuture.runAsync(() -> redisTemplate.opsForValue().set(key, value));
    }

    public CompletableFuture<V> getValue(@NonNull final K key) {
        log.info("Retrieving {} from cache.", key);
        return CompletableFuture.supplyAsync(() -> redisTemplate.opsForValue().get(key));
    }

}

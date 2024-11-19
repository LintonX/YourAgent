package com.youragent.service.redisservice;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RedisServiceImpl<K, V> implements RedisService<K, V>{

    private final RedisTemplate<K, V> redisTemplate;

    public RedisServiceImpl(@NonNull RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(@NonNull final K key, @NonNull final V value) {
        log.info("Storing {} : {} in cache.", key, value);
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(@NonNull final K key,
                         @NonNull final V value,
                         @NonNull final Duration ttl) {
        log.info("Storing {} : {} in cache with TTL of {}.", key, value, ttl);
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public V getValue(@NonNull final K key) {
        log.info("Retrieving {} from cache.", key);
        return redisTemplate.opsForValue().get(key);
    }
}

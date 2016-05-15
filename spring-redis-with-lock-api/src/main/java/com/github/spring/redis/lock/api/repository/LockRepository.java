package com.github.spring.redis.lock.api.repository;

/**
 * Created by cenkakin
 */
public interface LockRepository {

    int incrementAndGet(String key, Long ttlInMilliSeconds);

    void removeWithKeyPattern(String key);
}

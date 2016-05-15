package com.github.spring.redis.lock.plug.repository;

import com.github.spring.redis.lock.api.repository.LockRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by cenkakin
 */
public class RedisLockRepository implements LockRepository {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisLockRepository(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public int incrementAndGet(String key, Long ttlInMilliSeconds) {
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(key, stringRedisTemplate.getConnectionFactory());
        redisAtomicInteger.expire(ttlInMilliSeconds, TimeUnit.MILLISECONDS);
        return redisAtomicInteger.incrementAndGet();
    }

    @Override
    public void removeWithKeyPattern(String keyPattern) {
        Set<String> keys = stringRedisTemplate.keys(keyPattern);
        stringRedisTemplate.delete(keys);
    }
}

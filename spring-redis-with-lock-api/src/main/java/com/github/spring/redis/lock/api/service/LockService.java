package com.github.spring.redis.lock.api.service;

import com.github.spring.redis.lock.api.LockAware;
import com.github.spring.redis.lock.api.exception.LockException;

import java.util.List;

/**
 * Created by cenkakin
 */
public interface LockService {

    void lock(String s, List<LockAware> keyList) throws LockException;

    void unlock(LockAware lockAware);

    void unlockAllObjectsWithActionPrefix(String actionPrefix);

    void unlockAllObjectsWithObjectPrefix(String objectPrefix);

}

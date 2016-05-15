package com.github.spring.redis.lock.plug.service;

import com.github.spring.redis.lock.api.LockAware;
import com.github.spring.redis.lock.api.exception.LockException;
import com.github.spring.redis.lock.api.repository.LockRepository;
import com.github.spring.redis.lock.api.service.LockService;
import com.github.spring.redis.lock.plug.configuration.LockProperties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cenkakin
 */
public class LockServiceImpl implements LockService {

    public static final String KEY_SPLITTER = ":";

    private final LockRepository lockRepository;
    private final LockProperties lockProperties;

    public LockServiceImpl(LockRepository lockRepository, LockProperties lockProperties) {
        this.lockRepository = lockRepository;
        this.lockProperties = lockProperties;
    }

    @Override
    public void lock(String keyPrefix, List<LockAware> lockAwares) throws LockException {
        lockAwares.forEach(la -> {
            String lockedKey = generateKey(lockProperties.getGlobalPrefix(), keyPrefix, la.keyObjectPrefix(), la.key());
            int result = lockRepository.incrementAndGet(lockedKey, lockProperties.getTtlInMilliSeconds());
            if (result > 1) {
                throw new LockException(lockedKey + " is already locked!");
            }
        });
    }

    @Override
    public void unlock(LockAware lockAware) {
        String lockedKey = generateKey(lockAware.keyObjectPrefix(), lockAware.key());
        lockRepository.removeWithKeyPattern("*" + lockedKey);
    }

    @Override
    public void unlockAllObjectsWithActionPrefix(String actionPrefix) {
        String lockedKey = generateKey(lockProperties.getGlobalPrefix(), actionPrefix);
        lockRepository.removeWithKeyPattern(lockedKey + "*");
    }

    @Override
    public void unlockAllObjectsWithObjectPrefix(String objectPrefix) {
        String lockedKey = generateKey(lockProperties.getGlobalPrefix(), "*", objectPrefix);
        lockRepository.removeWithKeyPattern(lockedKey + "*");
    }

    private String generateKey(String... keys) {
        return Arrays.asList(keys).stream().filter(k -> !k.isEmpty()).collect(Collectors.joining(KEY_SPLITTER));
    }
}

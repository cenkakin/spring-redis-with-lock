package com.github.spring.redis.lock.api.exception;

/**
 * Created by cenkakin
 */
public class LockException extends RuntimeException {

    public LockException(String message) {
        super(message);
    }
}

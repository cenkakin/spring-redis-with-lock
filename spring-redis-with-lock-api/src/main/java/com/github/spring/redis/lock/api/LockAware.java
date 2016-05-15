package com.github.spring.redis.lock.api;

/**
 * Created by cenkakin
 */
public interface LockAware {

    String key();

    default String keyObjectPrefix()  { return  "";}
}

package com.github.spring.redis.lock.plug.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by cenkakin
 */
@ConfigurationProperties(prefix = "redis.lock")
public class LockProperties {

    private String globalPrefix = "";

    private Long ttlInMilliSeconds = 180000L;

    public String getGlobalPrefix() {
        return globalPrefix;
    }

    public void setGlobalPrefix(String globalPrefix) {
        this.globalPrefix = globalPrefix;
    }

    public Long getTtlInMilliSeconds() {
        return ttlInMilliSeconds;
    }

    public void setTtlInMilliSeconds(Long ttlInMilliSeconds) {
        this.ttlInMilliSeconds = ttlInMilliSeconds;
    }
}

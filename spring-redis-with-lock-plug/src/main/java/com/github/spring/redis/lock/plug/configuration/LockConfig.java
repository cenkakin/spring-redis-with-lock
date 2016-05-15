package com.github.spring.redis.lock.plug.configuration;

import com.github.spring.redis.lock.api.repository.LockRepository;
import com.github.spring.redis.lock.api.service.LockService;
import com.github.spring.redis.lock.plug.core.LockBeanPostProcessor;
import com.github.spring.redis.lock.plug.repository.RedisLockRepository;
import com.github.spring.redis.lock.plug.service.LockServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by cenkakin
 */
@Configuration
@EnableConfigurationProperties(LockProperties.class)
public class LockConfig {

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public LockService lockService(LockProperties lockProperties, StringRedisTemplate stringRedisTemplate) {
        LockRepository lockRepository = new RedisLockRepository(stringRedisTemplate);
        return new LockServiceImpl(lockRepository, lockProperties);
    }

    @Bean
    public LockBeanPostProcessor lockBeanPostProcessor(LockService lockService) {
        return new LockBeanPostProcessor(lockService);
    }
}

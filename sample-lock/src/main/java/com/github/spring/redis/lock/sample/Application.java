package com.github.spring.redis.lock.sample;

import com.github.spring.redis.lock.plug.configuration.LockConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by cenkakin
 */
@SpringBootApplication
@Import(LockConfig.class)
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

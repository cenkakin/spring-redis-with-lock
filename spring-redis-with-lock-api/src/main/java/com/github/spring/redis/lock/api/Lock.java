package com.github.spring.redis.lock.api;

import java.lang.annotation.*;

/**
 * Created by cenkakin
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    String keyActionPrefix() default "";

}

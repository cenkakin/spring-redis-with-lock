package com.github.spring.redis.lock.sample.model;

import com.github.spring.redis.lock.api.LockAware;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by cenkakin
 */
public class SampleObject implements LockAware, Serializable {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String key() {
        return Objects.nonNull(id) ? this.id.toString() : "";
    }

    @Override
    public String keyObjectPrefix() {
        return Objects.nonNull(name) ? this.name : "";
    }
}

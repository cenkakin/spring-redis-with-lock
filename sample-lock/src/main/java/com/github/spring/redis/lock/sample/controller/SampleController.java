package com.github.spring.redis.lock.sample.controller;

import com.github.spring.redis.lock.api.Lock;
import com.github.spring.redis.lock.plug.service.LockServiceImpl;
import com.github.spring.redis.lock.sample.model.SampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cenkakin
 */
@RestController
public class SampleController {

    @Autowired
    LockServiceImpl lockService;

    @Lock(keyActionPrefix = "lockaction")
    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    public ResponseEntity lock(@RequestBody SampleObject sampleObject) {
        return ResponseEntity.ok().build();
    }

}

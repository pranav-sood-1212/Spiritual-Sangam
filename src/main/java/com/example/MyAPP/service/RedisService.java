package com.example.MyAPP.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
    public <T> T get(String key,Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(o.toString(), entityClass);
        }catch (Exception e){
            log.info("your streak is broken");
            return null;
        }
    }
    public void set(String key,Object value,int timeout){
        try {
            redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
        }catch (Exception e){
            log.info("error in updating streak");
        }
    }
}

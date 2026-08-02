package com.sangam.connect.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
    // Redis accepts only key value pairs
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Bean     // very important otherwise spring create a bean for this
    public StringRedisTemplate redisConfigFunc(){
        // this helped to use the string redis template which have 4 fixed serializers all string serializers(can see in source code).
        // this means now we can pass only Strings to redis all key ,value,hash key,hash value.
        // so if we have a java object we need to manage to convert it into string (by object mapper or anything else) then pass to redis.
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate(redisConnectionFactory);
        return stringRedisTemplate;
    }
}

package com.example.MyAPP.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomCacheErrorHandler implements CacheErrorHandler {
    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.trace("Redis is DOWN - Falling back to MongoDB for key: " + key);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.trace("Redis is DOWN - Falling back to MongoDB for key: " + key);

    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.trace("Redis is DOWN - Falling back to MongoDB for key: " + key);

    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.trace("Redis is DOWN ");

    }
}

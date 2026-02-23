package com.example.MyAPP.config;

import com.example.MyAPP.cache.CustomCacheErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig implements CachingConfigurer {
    @Autowired
    private CustomCacheErrorHandler customCacheErrorHandler;

    @Override
    public CacheErrorHandler errorHandler(){
       return customCacheErrorHandler;
    }

}

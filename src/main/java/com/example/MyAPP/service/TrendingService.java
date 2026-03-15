package com.example.MyAPP.service;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.enums.SourceType;
import com.example.MyAPP.repository.BhajanRepository;
import com.example.MyAPP.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TrendingService {
    private final BhajanRepository bhajanRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public TrendingService(BhajanRepository bhajanRepository,StringRedisTemplate stringRedisTemplate) {
        this.bhajanRepository = bhajanRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public List<Bhajan> getTrending(String redisKey, SourceType type) {
        // 1. Fetch Top 10 IDs from Redis Sorted Set
        Set<String> topIds = stringRedisTemplate.opsForZSet().reverseRange(redisKey, 0, 9);

        if (topIds != null && !topIds.isEmpty()) {
            // 2. Fetch full objects from Mongo (Preserving the order is tricky, but this works)
            List<Bhajan> bhajans = bhajanRepository.findAllById(topIds);

            // Match the order of Redis IDs
            List<String> orderList = new ArrayList<>(topIds);
            bhajans.sort(Comparator.comparingInt(b -> orderList.indexOf(b.getId())));

            return bhajans;
        }

        // 3. Fallback: If Redis is empty, hit the DB directly (your original code)
        if (type == null) return bhajanRepository.findTop10ByOrderByLikesDesc();
        return bhajanRepository.findTop10BySourceTypeOrderByLikesDesc(type);
    }

    // Your clean API methods:
    public List<Bhajan> getCommonTrending() {
        return getTrending("trending:global", null);
    }

    public List<Bhajan> getYouTubeTrending() {
        return getTrending("trending:youtube", SourceType.YOUTUBE);
    }

    public List<Bhajan> getLocalTrending() {
        return getTrending("trending:local", SourceType.LOCAL);
    }
}

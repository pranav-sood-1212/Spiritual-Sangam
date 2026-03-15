package com.example.MyAPP.service;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.enums.SourceType;
import com.example.MyAPP.repository.BhajanRepository;
import com.example.MyAPP.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FavoriteService {


    private final RedisTemplate<String,Object> redisTemplate;
    private final KafkaProducerService kafkaProducerService;
    private final BhajanRepository bhajanRepository;
    private final UserRepository userRepository;

    public FavoriteService(RedisTemplate<String,Object> redisTemplate, KafkaProducerService kafkaProducerService, BhajanRepository bhajanRepository, UserRepository userRepository) {
        this.kafkaProducerService = kafkaProducerService;
        this.bhajanRepository = bhajanRepository;
        this.userRepository = userRepository;
        this.redisTemplate=redisTemplate;
    }


    @Transactional
    @CacheEvict(value = "favorites", key = "#username")
    public boolean like(String bhajanId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow();
        if(!user.getMyFavorites().contains(bhajan)) {
            user.getMyFavorites().add(bhajan);
            bhajan.setLikes(bhajan.getLikes()+1);
            kafkaProducerService.sendActivityLog(user.getUsername(), bhajan.getId(),"Like");
            userRepository.save(user);
            bhajanRepository.save(bhajan);
            try{
                redisTemplate.opsForZSet().incrementScore("trending:global", bhajan.getId(), 1);

                // 2. Conditionally update Category-specific Trending lists
                if (bhajan.getSourceType() == SourceType.LOCAL) {
                    redisTemplate.opsForZSet().incrementScore("trending:local", bhajan.getId(), 1);
                } else if (bhajan.getSourceType() == SourceType.YOUTUBE) {
                    redisTemplate.opsForZSet().incrementScore("trending:youtube", bhajan.getId(), 1);
                }
            }catch (Exception e){
                log.error("Redis leaderboard update failed", e);
            }
            return true;
        }else{
            return false;
        }
    }


    @Transactional
    @CacheEvict(value = "favorites", key = "#username")
    public boolean unLike(String bhajanId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow();
        if(user.getMyFavorites().contains(bhajan)) {
            user.getMyFavorites().remove(bhajan);
            bhajan.setLikes(bhajan.getLikes()-1);
            kafkaProducerService.sendActivityLog(user.getUsername(), bhajan.getId(),"Unlike");

            userRepository.save(user);
            bhajanRepository.save(bhajan);
            try{
                redisTemplate.opsForZSet().incrementScore("trending:global", bhajan.getId(), -1);

                // 2. Conditionally update Category-specific Trending lists
                if (bhajan.getSourceType() == SourceType.LOCAL) {
                    redisTemplate.opsForZSet().incrementScore("trending:local", bhajan.getId(), -1);
                } else if (bhajan.getSourceType() == SourceType.YOUTUBE) {
                    redisTemplate.opsForZSet().incrementScore("trending:youtube", bhajan.getId(), -1);
                }
            }catch (Exception e){
                log.info("redis down but bhajan unliked");
            }
            return true;
        }else{
            return false;
        }
    }



    @Cacheable(value = "favorites",key = "#username")
    public List<Bhajan> favorites(String username){
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        return user.getMyFavorites();
    }
}

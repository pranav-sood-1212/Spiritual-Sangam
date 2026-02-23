package com.example.MyAPP.service;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.exception.BhajanNotFoundException;
import com.example.MyAPP.repository.BhajanRepository;
import com.example.MyAPP.repository.UserRepository;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.*;
import java.util.*;

@Slf4j
@Service
public class BhajanService {
    private final CategoryService categoryService;
    private final MoodMappingService moodMappingService;
    private final RedisTemplate<String,Object> redisTemplate;
    private final YoutubeService youtubeService;
    private final KafkaProducerService kafkaProducerService;
    private final BhajanRepository bhajanRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;

    public BhajanService(CategoryService categoryService, MoodMappingService moodMappingService, RedisTemplate<String,Object> redisTemplate, YoutubeService youtubeService, KafkaProducerService kafkaProducerService, BhajanRepository bhajanRepository, UserRepository userRepository, RedisService redisService) {
        this.categoryService = categoryService;
        this.moodMappingService = moodMappingService;
        this.youtubeService = youtubeService;
        this.kafkaProducerService = kafkaProducerService;
        this.bhajanRepository = bhajanRepository;
        this.userRepository = userRepository;
        this.redisTemplate=redisTemplate;
        this.redisService = redisService;
    }
    @Transactional
    @CacheEvict(value = "favorites2", key = "#username")
    public boolean like(Bhajan bhajan, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        if(!user.getMyFavorites().contains(bhajan)) {
            user.getMyFavorites().add(bhajan);
            bhajan.setLikes(bhajan.getLikes()+1);
            kafkaProducerService.sendActivityLog(user.getUsername(), bhajan.getBhajanId(),"Like");
            userRepository.save(user);
            bhajanRepository.save(bhajan);
            try{
                redisTemplate.opsForZSet().incrementScore("trending_bhajans",bhajan.getBhajanId(),1);
            }catch (Exception e){
               log.info("redis down but bhajan liked");
            }
            return true;
        }else{
            return false;
        }
    }
    @Transactional
    @CacheEvict(value = "favorites2", key = "#username")
    public boolean unLike(Bhajan bhajan, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        if(user.getMyFavorites().contains(bhajan)) {
            user.getMyFavorites().remove(bhajan);
            bhajan.setLikes(bhajan.getLikes()-1);
            kafkaProducerService.sendActivityLog(user.getUsername(), bhajan.getBhajanId(),"Unlike");

            userRepository.save(user);
            bhajanRepository.save(bhajan);
            try{
                redisTemplate.opsForZSet().incrementScore("trending_bhajans",bhajan.getBhajanId(),-1);
            }catch (Exception e){
                log.info("redis down but bhajan liked");
            }
            return true;
        }else{
            return false;
        }
    }

    @Cacheable(value = "favorites2",key = "#username")
    public List<Bhajan> favorites(String username){
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        return user.getMyFavorites();
    }


    public void update(Bhajan bhajanInDb, Bhajan newBhajan) {
        if(!Objects.equals(bhajanInDb.getCategory(), newBhajan.getCategory())) bhajanInDb.setCategory(newBhajan.getCategory());
        if(!Objects.equals(bhajanInDb.getDescription(), newBhajan.getDescription()))bhajanInDb.setDescription(newBhajan.getDescription());
        if(!Objects.equals(bhajanInDb.getTitle(), newBhajan.getTitle()))bhajanInDb.setTitle(newBhajan.getTitle());
        if(!Objects.equals(bhajanInDb.getThumbnail(), newBhajan.getThumbnail()))bhajanInDb.setThumbnail(newBhajan.getThumbnail());
    }



    public List<Bhajan> search(String query) {
        List<Bhajan> bhajans= bhajanRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                query,query,query
        );
        if(bhajans.isEmpty()){
            List<SearchResult> listBhajans= youtubeService.searchThere(query);
            if(listBhajans!=null) {
                for (SearchResult s : listBhajans) {
                    Bhajan bhajan = new Bhajan(
                            s.getId().getVideoId(),
                            s.getSnippet().getTitle(),
                            s.getSnippet().getDescription(),
                            categoryService.determineCategory(s.getSnippet().getTitle(),s.getSnippet().getDescription()),
                            s.getSnippet().getThumbnails().getHigh().getUrl(),
                            s.getId().getVideoId(),
                            moodMappingService.determineMood(s.getSnippet().getTitle(),s.getSnippet().getDescription()),
                            0
                            );
                    bhajans.add(bhajan);
                }
                for(Bhajan b:bhajans){
                    if(!bhajanRepository.existsById(b.getBhajanId())){
                        bhajanRepository.save(b);
                    }
                }
            }
        }

        return bhajans;
    }



    public void deleteBhajan(String bhajanId) {
        bhajanRepository.deleteById(bhajanId);
        try{
            redisTemplate.opsForZSet().remove("trending_bhajans",bhajanId);
        }catch(Exception e){
            log.error("Redis down: Could not remove bhajan from trending list, but DB record deleted.");
        }
    }
    public void enritcher(String bhajanId) throws IOException {
        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow(()-> new BhajanNotFoundException("no such bhajan exist"));
//        Video youtubeVideo=youtubeService.getVideoDetails(bhajan.getVideoId());
        Video youtubeVideo=youtubeService.getVideoDetails(bhajan.getBhajanId());
        if (youtubeVideo != null) {
            // 3. Map Snippet data
            bhajan.setTitle(youtubeVideo.getSnippet().getTitle());
            bhajan.setDescription(youtubeVideo.getSnippet().getDescription());

            // Take the high-res thumbnail
            String highResThumb = youtubeVideo.getSnippet().getThumbnails().getHigh().getUrl();
            bhajan.setThumbnail(highResThumb);

            //set category
            bhajan.setCategory(categoryService.determineCategory(youtubeVideo.getSnippet().getTitle(),youtubeVideo.getSnippet().getDescription()));
            //set mood
            bhajan.setMood(moodMappingService.determineMood(youtubeVideo.getSnippet().getTitle(),youtubeVideo.getSnippet().getDescription()));

            bhajan.setVideoId(youtubeVideo.getId());
            // 4. Save to Repository
            bhajanRepository.save(bhajan);
        }
    }


    public void batchEnritcher(){
        List<Bhajan> bhajans=bhajanRepository.findAll();
        for(Bhajan bhajan:bhajans){
            try{
                enritcher(bhajan.getBhajanId());
            }catch (IOException e){
                log.error("cannot enrich this");
            }
        }
    }


//    @Scheduled(cron = "0 0 3 * * SUN") // Runs every Sunday at 3 AM
    public void periodicEnrichment() {
        log.info("Starting scheduled background enrichment...");
        batchEnritcher();
    }


    public Bhajan alarmBhajan(String preference) {
        Bhajan candidate=bhajanRepository.findById(preference).orElse(null);
        if(candidate==null){
            List<Bhajan> morningBhajans=bhajanRepository.findByMood("morning aartis");
            if(!morningBhajans.isEmpty()) {
                int randIdx = (int) (Math.random() * morningBhajans.size());
                candidate = morningBhajans.get(randIdx);
            }else{
                // Absolute fallback: If no morning aartis exist, get any random bhajan
                List<Bhajan> all = bhajanRepository.findAll();
                candidate = all.get((int) (Math.random() * all.size()));
            }
        }
        return candidate;
    }


    public void streakFeature(User user) {
        LocalDate now = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        // TTL Math: Seconds until the end of tomorrow (172,800 total seconds in 2 days)
        long ttlInSeconds = 172800L - nowTime.toSecondOfDay();

        // Fetch last activity from Redis
        LocalDate lastStreakDate = redisService.get(user.getId(), LocalDate.class);

        // PHASE 1: Handle New Users or Total Expiry
        if (lastStreakDate == null) {
            handleGapOrNewUser(user, now);
        }

        // PHASE 2: Handle Same-Day Returns (Scenario A)
        else if (now.equals(lastStreakDate)) {
            log.info("User {} already checked in today. No increment, just refreshing TTL.", user.getUsername());
        }

        // PHASE 3: Handle Progress or Gaps (Scenario B)
        else if (now.isAfter(lastStreakDate)) {

            // SUCCESS: Exactly one day later
            if (lastStreakDate.plusDays(1).equals(now)) {
                user.setStreak(user.getStreak() + 1);

                // Check for Recovery Merge (Phase C)
                if (user.isInRecoveryMode() && user.getStreak() >= 7) {
                    log.info("Recovery Successful for {}! Merging streaks.", user.getUsername());
                    user.setStreak(user.getPendingStreak() + user.getStreak());
                    user.setPendingStreak(0);
                    user.setInRecoveryMode(false);
                }
            }
            // FAILURE: Gap is > 1 day
            else {
                handleGapOrNewUser(user, now);
            }
            userRepository.save(user);
        }

        // ALWAYS: Push the expiration window forward
        redisService.set(user.getId(), now, (int) (ttlInSeconds));
    }

    /**
     * Helper to handle when a streak breaks or a new user starts.
     * Incorporates the "Recovery Trigger" for high streaks.
     */
    private void handleGapOrNewUser(User user, LocalDate now) {
        if (user.getStreak() >= 30) {
            log.info("High streak broken for {}. Entering Recovery Mode.", user.getUsername());
            user.setPendingStreak(user.getStreak());
            user.setInRecoveryMode(true);
            user.setChallengeStart(now);
        } else {
            // If they were already in recovery and failed again, wipe it.
            user.setInRecoveryMode(false);
            user.setPendingStreak(0);
        }

        user.setStreak(1);
        userRepository.save(user);
    }


    @Scheduled(cron = "0 0 20 * * *")
    public void notifyToSaveStreak(){
        List<User> users=userRepository.findByLastActivityDate(LocalDate.now().minusDays(1));
        for(User user:users){
            if (user.isInRecoveryMode()) {
                // Nudge for the recovery challenge
                log.info("Nudging {} to continue their recovery challenge!", user.getUsername());
                kafkaProducerService.sendNotificationForStreakSave(user, "hey you didn't listened to any bhajan today you will loose the challenge come fast");

            } else {
                // Standard nudge
                log.info("Nudging {} to save their 🔥 streak!", user.getUsername());
                kafkaProducerService.sendNotificationForStreakSave(user, "hey you didn't listened to any bhajan today come fast");
            }
        }


    }





}

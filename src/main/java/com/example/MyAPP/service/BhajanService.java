package com.example.MyAPP.service;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.enums.SourceType;
import com.example.MyAPP.exception.BhajanNotFoundException;
import com.example.MyAPP.repository.BhajanRepository;
import com.example.MyAPP.repository.UserRepository;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.*;
import java.util.*;

@Slf4j
@Service
public class BhajanService {
    private final DeityService deityService;
    private final MoodMappingService moodMappingService;
    private final RedisTemplate<String,Object> redisTemplate;
    private final YoutubeService youtubeService;
    private final KafkaProducerService kafkaProducerService;
    private final BhajanRepository bhajanRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final StringRedisTemplate stringRedisTemplate;

    public BhajanService(DeityService deityService, MoodMappingService moodMappingService, RedisTemplate<String,Object> redisTemplate, YoutubeService youtubeService, KafkaProducerService kafkaProducerService, BhajanRepository bhajanRepository, UserRepository userRepository, RedisService redisService, StringRedisTemplate stringRedisTemplate) {
        this.deityService = deityService;
        this.moodMappingService = moodMappingService;
        this.youtubeService = youtubeService;
        this.kafkaProducerService = kafkaProducerService;
        this.bhajanRepository = bhajanRepository;
        this.userRepository = userRepository;
        this.redisTemplate=redisTemplate;
        this.redisService = redisService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public List<Bhajan> getYoutubeMusic(){
        return bhajanRepository.findBySourceType(SourceType.YOUTUBE);
    }

    @Cacheable(value = "music_local", key = "'all_local'")
    public List<Bhajan> getLocalMusic(){
        return bhajanRepository.findBySourceType(SourceType.LOCAL);
    }

    @CacheEvict(value = "music_local", allEntries = true, condition = "#bhajan.sourceType.name() == 'LOCAL'")
    public Bhajan saveBhajan(Bhajan bhajan) {
        return bhajanRepository.save(bhajan);
    }





    @GetMapping("/searchInMood")
    public ResponseEntity<List<Bhajan>> searchBhajans(@RequestParam String query) {
        // Logic: For your "peek" search option.
        // You'll need findByTitleContainingIgnoreCase in your repository for this.
        return ResponseEntity.ok(bhajanRepository.findByTitleContainingIgnoreCaseOrDeityContainingIgnoreCaseOrArtistNameContainingIgnoreCase(query,query,query));
    }





    public void update(Bhajan bhajanInDb, Bhajan newBhajan) {
        if(!Objects.equals(bhajanInDb.getDeity(), newBhajan.getDeity())) bhajanInDb.setDeity(newBhajan.getDeity());
        if(!Objects.equals(bhajanInDb.getLyrics(), newBhajan.getLyrics()))bhajanInDb.setLyrics(newBhajan.getLyrics());
        if(!Objects.equals(bhajanInDb.getTitle(), newBhajan.getTitle()))bhajanInDb.setTitle(newBhajan.getTitle());
        if(!Objects.equals(bhajanInDb.getThumbnail(), newBhajan.getThumbnail()))bhajanInDb.setThumbnail(newBhajan.getThumbnail());
    }



    public List<Bhajan> search(String query, SourceType filter) {
        List<Bhajan> results = new ArrayList<>();

        if (filter == null) {
            // SCENARIO 1: Global Search (Look everywhere)
            results = bhajanRepository.findByTitleContainingIgnoreCaseOrDeityContainingIgnoreCaseOrArtistNameContainingIgnoreCase(query, query, query);

            // If empty, discover from YouTube
            if (results.isEmpty()) {
                results = discoverFromYoutube(query);
            }
        }
        else if (filter == SourceType.LOCAL) {
            // SCENARIO 2: Local Only (Strict DB check)
            results = bhajanRepository.findBySourceTypeAndTitleContainingIgnoreCase(SourceType.LOCAL, query);
        }
        else if (filter == SourceType.YOUTUBE) {
            // SCENARIO 3: YouTube Only (DB first, then API)
            results = bhajanRepository.findBySourceTypeAndTitleContainingIgnoreCase(SourceType.YOUTUBE, query);
            if (results.isEmpty()) {
                results = discoverFromYoutube(query);
            }
        }

        return results;
    }

    private List<Bhajan> discoverFromYoutube(String query) {
        List<Bhajan> finalList = new ArrayList<>();
        List<SearchResult> ytList = youtubeService.searchThere(query);

        if (ytList == null || ytList.isEmpty()) return finalList;

        for (SearchResult currBhajan : ytList) {
            String videoId = currBhajan.getId().getVideoId();

            // 1. Check DB First - Don't call YouTube API again if we have it!
            Optional<Bhajan> existing = bhajanRepository.findById(videoId);
            if (existing.isPresent()) {
                finalList.add(existing.get());
                continue; // Skip the heavy API calls below
            }

            try {
                Video video = youtubeService.getVideoDetails(videoId);
                if (video == null) continue;

                // 2. Safe Singer Extraction
                List<String> tags = video.getSnippet().getTags();
                String singer = (tags != null && tags.size() >= 2)
                        ? tags.get(1)
                        : video.getSnippet().getChannelTitle();

                Bhajan b = Bhajan.builder()
                        .id(videoId)
                        .title(currBhajan.getSnippet().getTitle())
                        .artistName(singer)
                        .lyrics("Currently not available")
                        .thumbnail(video.getSnippet().getThumbnails().getHigh().getUrl()) // Better quality from Video object
                        .videoIdOrUrl(videoId)
                        .likes(0)
                        .deity(deityService.determineCategory(currBhajan.getSnippet().getTitle(),currBhajan.getSnippet().getDescription())) // Better than just "deity"
                        .mood(moodMappingService.determineMoods(currBhajan.getSnippet().getTitle(),currBhajan.getSnippet().getDescription()))
                        .sourceType(SourceType.YOUTUBE)
                        .build();

                bhajanRepository.save(b);
                finalList.add(b);

            } catch (IOException e) {
                // Log the error but don't crash the whole search for one bad video
                System.err.println("Could not enrich video: " + videoId);
            }
        }
        return finalList;
    }


    public void deleteBhajan(String bhajanId) {
        bhajanRepository.deleteById(bhajanId);
        try{
            redisTemplate.opsForZSet().remove("trending:global",bhajanId);
        }catch(Exception e){
            log.error("Redis down: Could not remove bhajan from trending list, but DB record deleted.");
        }
    }
//    public void enritcher(String bhajanId) throws IOException {
//        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow(()-> new BhajanNotFoundException("no such bhajan exist"));
////        Video youtubeVideo=youtubeService.getVideoDetails(bhajan.getVideoId());
//        Video youtubeVideo=youtubeService.getVideoDetails(bhajan.getId());
//        if (youtubeVideo != null) {
//            // 3. Map Snippet data
//            bhajan.setTitle(youtubeVideo.getSnippet().getTitle());
//            bhajan.setLyrics(youtubeVideo.getSnippet().getDescription());
//
//            // Take the high-res thumbnail
//            String highResThumb = youtubeVideo.getSnippet().getThumbnails().getHigh().getUrl();
//            bhajan.setThumbnail(highResThumb);
//
//            //set category
//            bhajan.setDeity(deityService.determineCategory(youtubeVideo.getSnippet().getTitle(),youtubeVideo.getSnippet().getDescription()));
//            //set mood
//            bhajan.setMood(moodMappingService.determineMood(youtubeVideo.getSnippet().getTitle(),youtubeVideo.getSnippet().getDescription()));
//
//            bhajan.setVideoIdOrUrl(youtubeVideo.getId());
//            // 4. Save to Repository
//            bhajanRepository.save(bhajan);
//        }
//    }


//    public void batchEnritcher(){
//        List<Bhajan> bhajans=bhajanRepository.findAll();
//        for(Bhajan bhajan:bhajans){
//            try{
//                enritcher(bhajan.getId());
//            }catch (IOException e){
//                log.error("cannot enrich this");
//            }
//        }
//    }


//    @Scheduled(cron = "0 0 3 * * SUN") // Runs every Sunday at 3 AM
//    public void periodicEnrichment() {
//        log.info("Starting scheduled background enrichment...");
//        batchEnritcher();
//    }


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

package com.example.MyAPP.controller;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.exception.BhajanNotFoundException;
import com.example.MyAPP.repository.BhajanRepository;
import com.example.MyAPP.repository.UserRepository;
import com.example.MyAPP.service.BhajanService;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/Bhajan")
public class BhajanController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private final UserRepository userRepository;
    private final BhajanService bhajanService;
    private final BhajanRepository bhajanRepository;

    public BhajanController(UserRepository userRepository, BhajanService bhajanService, BhajanRepository bhajanRepository) {
        this.userRepository = userRepository;
        this.bhajanService = bhajanService;
        this.bhajanRepository = bhajanRepository;
    }

    @GetMapping("/getAllBhajans")
    public ResponseEntity<?> getAllBhajans(){
        List<Bhajan> all= bhajanRepository.findAll();
        if(all.size()>0) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No bhajan Yet",HttpStatus.OK);
        }
    }
    @GetMapping("/getBhajanById/{bhajanId}")
    public ResponseEntity<Bhajan> getBhajanById(@PathVariable String bhajanId){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow(()->new BhajanNotFoundException("no such bhajan exist"));
        bhajanService.streakFeature(user);
        return new ResponseEntity<>(bhajan,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateBhajanById/{bhajanId}")
    public ResponseEntity<Bhajan> updateBhajanById(@PathVariable String bhajanId,@RequestBody Bhajan newBhajan){
        Bhajan bhajanInDb=bhajanRepository.findById(bhajanId).orElseThrow(()->new BhajanNotFoundException("no such bhajan exist"));
        bhajanService.update(bhajanInDb,newBhajan);
        return new ResponseEntity<>(bhajanInDb,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addNewBhajan")
    public ResponseEntity<Bhajan> addNewBhajan(@RequestBody Bhajan bhajan){
//        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
//        String username=auth.getName();
//        Optional<User> user=userRepository.findByUsername(username);
//        if(user.isPresent()) {
//            List<String> userRoles = user.get().getRoles();
//            boolean mark=false;
//            for (String roles : userRoles){
//                if(roles.equals("ROLE_ADMIN")){
//                    mark=true;
//                }
//            }
//            if(mark) {
//                bhajanRepository.save(bhajan);
//                return new ResponseEntity<>(bhajan, HttpStatus.ACCEPTED);
//            }
//        }
        bhajanRepository.save(bhajan);
        return new ResponseEntity<>(bhajan, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteBhajanById/{bhajanId}")
    @Transactional
    public ResponseEntity<String> deleteBhajanById(@PathVariable String bhajanId){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow(()->new BhajanNotFoundException("no such bhajan exist"));
        user.getMyFavorites().remove(bhajan);
        userRepository.save(user);
        bhajanService.deleteBhajan(bhajanId);

        return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
    }




    @GetMapping("/getMyFavorites")
    public ResponseEntity<List<Bhajan>> getMyFavorites() {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(bhajanService.favorites(username),HttpStatus.OK);

    }
    @DeleteMapping("/unLikeBhajan/{bhajanId}")
    public ResponseEntity<String> unLikeBhajan(@PathVariable String bhajanId) {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow(()->new BhajanNotFoundException("no such bhajan exist"));
        if(bhajanService.unLike(bhajan,username)) return new ResponseEntity<>("Bhajan unliked successfully and removed from myFavorites",HttpStatus.OK);
        else return new ResponseEntity<>("Bhajan already unLiked",HttpStatus.OK);
    }

    @GetMapping("/searchBhajan")
    public ResponseEntity<?> searchBhajan(@RequestParam("q") String query){
        List<Bhajan> bhajanList=bhajanService.search(query);
        if(!bhajanList.isEmpty()){
            return new ResponseEntity<>(bhajanList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("nothing for this search",HttpStatus.OK);
        }
    }

    @GetMapping("/getTrendingBhajans")
    public ResponseEntity<?> getTrendingBhajans(){
        try{
            Set<Object> trendingList=redisTemplate.opsForZSet().reverseRange("trending_bhajans",0,2);
            return new ResponseEntity<>(trendingList,HttpStatus.OK);
        }catch(Exception e){
            List<Bhajan> trendingList=bhajanRepository.findTop3ByOrderByLikesDesc();
            return new ResponseEntity<>("redis down :"+trendingList,HttpStatus.OK);
        }

    }


    @GetMapping("/enrich-all")
    public String triggerEnrichmentAll() {
        bhajanService.batchEnritcher();
        return "Enrichment process started! Check your console/database for updates.";

    }
    @GetMapping("/enrich/{bhajanId}")
    public String triggerEnrichment(@PathVariable String bhajanId) {
        try {
            bhajanService.enritcher(bhajanId);
        }catch(Exception e){
            System.out.println("errror");
        }
        return "Enrichment process started! Check your console/database for updates.";

    }

    @PostMapping("/setAlarm")
    public ResponseEntity<String> setAlarm(@RequestParam String bhajanId, @RequestParam String time){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        user.setAlarmTime(time);
        user.setAlarmPreference(bhajanId);
        userRepository.save(user);
        return ResponseEntity.ok("Alarm set for " + time);

    }

    @GetMapping("getAlarmBhajan")
    public ResponseEntity<Bhajan> getAlarmBhajan(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));

        // Reuse your existing smart logic!
        Bhajan wakeUpBhajan = bhajanService.alarmBhajan(user.getAlarmPreference());

        return ResponseEntity.ok(wakeUpBhajan);

    }

    @GetMapping("/getStreak")
    public ResponseEntity<Integer> getStreak(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        return new ResponseEntity<>(user.getStreak(),HttpStatus.OK);
    }


    @GetMapping("/is-favorite")
    public ResponseEntity<Boolean> checkFavorite(
            @RequestParam String username,
            @RequestParam String bhajanId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Assuming your User entity has a List<String> or Set<String> favoriteBhajanIds
        boolean isFav = user.getMyFavorites().contains(bhajanId);

        return ResponseEntity.ok(isFav);
    }






}

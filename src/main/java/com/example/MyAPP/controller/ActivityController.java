package com.example.MyAPP.controller;

import com.example.MyAPP.dto.ActivityLogDto;
import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.exception.BhajanNotFoundException;
import com.example.MyAPP.repository.BhajanRepository;
import com.example.MyAPP.repository.UserRepository;
import com.example.MyAPP.service.BhajanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:3000") // Logic: Allows React to talk to Java
public class ActivityController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BhajanRepository bhajanRepository;

    @Autowired
    private BhajanService bhajanService;

    @PostMapping("/likeAndUnlikeBhajan/{bhajanId}")
    @Transactional
    public ResponseEntity<?> likeAndUnlikeBhajan(@PathVariable String bhajanId) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        Bhajan bhajan=bhajanRepository.findById(bhajanId).orElseThrow(()->new BhajanNotFoundException("no such bhajan exist"));
        Map<String, String> response = new HashMap<>();
        if (bhajanService.like(bhajan, username)) {
            response.put("status", "liked");
            response.put("message", "Added to favorites");
            return ResponseEntity.ok(response);

        } else {
            bhajanService.unLike(bhajan, username); // Perform the unlike logic
            response.put("status", "unliked");
            response.put("message", "Removed from favorites");
            return ResponseEntity.ok(response);

        }

    }
    @GetMapping("/isFavorite/{bhajanId}")
    public boolean isFavorite(@PathVariable String bhajanId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 1. Logic: Handle the "Anonymous" case
        if (username == null || username.equals("anonymousUser")) {
            return false;
        }

        // 2. Logic: Safety check for the list
        List<Bhajan> favList = bhajanService.favorites(username);
        if (favList == null) {
            return false;
        }

        // 3. Logic: Safe stream comparison
        return favList.stream()
                .filter(b -> b != null && b.getBhajanId() != null)
                .anyMatch(b -> b.getBhajanId().equals(bhajanId));
    }

    @GetMapping("/mood/{moodName}")
    public ResponseEntity<List<Bhajan>> getBhajansByMood(@PathVariable String moodName) {
        // Logic: Querying MongoDB for documents where 'mood' matches the chip clicked.
        List<Bhajan> bhajans = bhajanRepository.findByMood(moodName);
        return ResponseEntity.ok(bhajans);
    }

    @GetMapping("/searchInMood")
    public ResponseEntity<List<Bhajan>> searchBhajans(@RequestParam String query) {
        // Logic: For your "peek" search option.
        // You'll need findByTitleContainingIgnoreCase in your repository for this.
        return ResponseEntity.ok(bhajanRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(query,query,query));
    }


    @GetMapping("/category/{deityName}")
    public ResponseEntity<List<Bhajan>> getBhajansByDeity(@PathVariable String deityName) {
        // 1. Logic: Use the IgnoreCase method
        List<Bhajan> bhajans = bhajanRepository.findByCategoryIgnoreCase(deityName);

        // 2. Logic: Return 200 OK with an empty list instead of null to prevent Frontend crashes
        if (bhajans == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(bhajans);
    }

}
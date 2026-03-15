package com.example.MyAPP.controller;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.exception.BhajanNotFoundException;
import com.example.MyAPP.repository.BhajanRepository;
import com.example.MyAPP.repository.UserRepository;
import com.example.MyAPP.service.BhajanService;
import com.example.MyAPP.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserRepository userRepository;

    private final BhajanRepository bhajanRepository;

    private final BhajanService bhajanService;

    private final FavoriteService favoriteService;

    public ActivityController(UserRepository userRepository, BhajanRepository bhajanRepository, BhajanService bhajanService,FavoriteService favoriteService) {
        this.userRepository = userRepository;
        this.bhajanRepository = bhajanRepository;
        this.bhajanService = bhajanService;
        this.favoriteService=favoriteService;
    }

    @PostMapping("/likeAndUnlikeBhajan/{bhajanId}")
    @Transactional
    public ResponseEntity<?> likeAndUnlikeBhajan(@PathVariable String bhajanId) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("you are not our user"));
        Map<String, String> response = new HashMap<>();
        if (favoriteService.like(bhajanId, username)) {
            response.put("status", "liked");
            response.put("message", "Added to favorites");
            return ResponseEntity.ok(response);

        } else {
            favoriteService.unLike(bhajanId, username); // Perform the unlike logic
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
        List<Bhajan> favList = favoriteService.favorites(username);
        if (favList == null) {
            return false;
        }

        // 3. Logic: Safe stream comparison
        return favList.stream()
                .filter(b -> b != null && b.getId() != null)
                .anyMatch(b -> b.getId().equals(bhajanId));
    }



}
package com.example.MyAPP.controller;


import com.example.MyAPP.dto.UserDashboardData;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public ResponseEntity<UserDashboardData> getHomeData() {
        // Logic: In a real app, you'd get the username from the SecurityContext
        // For now, let's fetch your specific user profile
        User user = userRepository.findByUsername("pranav")
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDashboardData data = new UserDashboardData(
                user.getUsername(),
                user.getStreak(),
                false,
                user.isInRecoveryMode(),
                "Ekadashi - A day for fasting and remembrance"
        );

        return ResponseEntity.ok(data);
    }
}
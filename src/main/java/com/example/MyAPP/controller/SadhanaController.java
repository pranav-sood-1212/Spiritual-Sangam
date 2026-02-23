package com.example.MyAPP.controller;

import com.example.MyAPP.dto.SadhanaLogDto;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.repository.UserRepository;
import com.example.MyAPP.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:3000") // Crucial to prevent CORS errors
public class SadhanaController {

    @PostMapping("/ritual")
    public ResponseEntity<?> updateRitual(@RequestBody SadhanaLogDto dto) {
        // Logic: Simulate processing for PEC Project testing
        System.out.println("--- DUMMY MODE ACTIVE ---");
        System.out.println("Received Ritual Update for User: " + dto.getUsername());
        System.out.println("Task: " + dto.getRitualName());
        System.out.println("Status: " + (dto.isCompleted() ? "Completed" : "Incomplete"));
        System.out.println("-------------------------");

        // Logic: Return a success map so React's 'try' block succeeds
        Map<String, String> response = new HashMap<>();
        response.put("message", "Dummy sync successful for " + dto.getRitualName());

        return ResponseEntity.ok(response);
    }
}
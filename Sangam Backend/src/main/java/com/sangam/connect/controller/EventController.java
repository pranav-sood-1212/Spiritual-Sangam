package com.sangam.connect.controller;

import com.sangam.connect.DTO.CommunityDetailsDTO;
import com.sangam.connect.DTO.EventDetailsDTO;
import com.sangam.connect.DTO.EventResponseDTO;
import com.sangam.connect.DTO.GeneralResponseDTO;
import com.sangam.connect.entity.EventEntity;
import com.sangam.connect.repository.EventRepo;
import com.sangam.connect.security.CustomUserDetails;
import com.sangam.connect.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/event")

public class EventController {
    private final EventService eventService;
    private final EventRepo eventRepo;


    public EventController(EventService eventService, EventRepo eventRepo) {
        this.eventService = eventService;
        this.eventRepo = eventRepo;
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponseDTO>> getUpcomingEvents(){
        List<EventEntity> upcomingEvents = eventRepo.findByExpectedDateAfter(LocalDate.now());
        List<EventResponseDTO> events=new ArrayList<>();
        if(upcomingEvents.isEmpty()) return new ResponseEntity<>(events,HttpStatus.OK);
        for(EventEntity upcomingEvent:upcomingEvents){
            events.add(new EventResponseDTO(upcomingEvent.getEventId(), upcomingEvent.getEventTitle(), upcomingEvent.getExpectedDate(),upcomingEvent.getStartTime(),upcomingEvent.getEndTime()));
        }
        return new ResponseEntity<>(events,HttpStatus.OK);
    }


    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvent(@RequestBody EventEntity eventEntity){
        if(eventService.add(eventEntity)) return new ResponseEntity<>("event added", HttpStatus.OK);
        else return new ResponseEntity<>("some error",HttpStatus.NOT_FOUND);

    }

    @GetMapping("/allEvents")
    public ResponseEntity<?> allEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsDTO> detailsOfEvent(@PathVariable String eventId){
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping("/{eventId}/register")
    public ResponseEntity<GeneralResponseDTO> registerEvent(@PathVariable String eventId,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        if(eventService.register(eventId,customUserDetails)) return ResponseEntity.ok(new GeneralResponseDTO("registered succefully"));
        else return ResponseEntity.status(HttpStatus.CONFLICT).body(new GeneralResponseDTO("you already registerd"));
    }



}

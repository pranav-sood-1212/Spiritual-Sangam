package com.sangam.connect.service;

import com.sangam.connect.DTO.CommunityDetailsDTO;
import com.sangam.connect.DTO.EventDetailsDTO;
import com.sangam.connect.DTO.EventResponseDTO;
import com.sangam.connect.entity.CommunityEntity;
import com.sangam.connect.entity.EventEntity;
import com.sangam.connect.entity.UserEntity;
import com.sangam.connect.repository.EventRepo;
import com.sangam.connect.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public boolean add(EventEntity eventEntity){
        eventRepo.save(eventEntity);
        return true;
    }

    public List<EventResponseDTO> getAllEvents(){
        List<EventEntity> eventEntities= eventRepo.findAll();
        List<EventResponseDTO> events=new ArrayList<>();
        for(EventEntity eventEntity:eventEntities){
            events.add(new EventResponseDTO(eventEntity.getEventId(), eventEntity.getEventTitle(), eventEntity.getExpectedDate() ,eventEntity.getStartTime() ,eventEntity.getEndTime()));
        }

        return events;
    }

    public EventDetailsDTO getEventById(String eventId) {
        EventEntity eventEntity=eventRepo.findById(eventId).orElseThrow(()-> new UsernameNotFoundException("no such event exists"));
        EventDetailsDTO eventDetailsDTO=new EventDetailsDTO(eventEntity.getEventId(),eventEntity.getEventTitle(),eventEntity.getDescription(),eventEntity.getExpectedDate(),eventEntity.getStartTime(),eventEntity.getEndTime(),eventEntity.getAddress(),eventEntity.getLocation(), eventEntity.getTotalSeats(), eventEntity.getLeftSeats());
        return eventDetailsDTO;
    }

    public boolean register(String eventId, CustomUserDetails customUserDetails) {
        String userName=customUserDetails.getUsername();
        EventEntity eventEntity=eventRepo.findById(eventId).orElseThrow(()->new UsernameNotFoundException("no such event"));
        if(eventEntity.getRegisteredUserNames().contains(userName)){
            return false;
        }
        eventEntity.getRegisteredUserNames().add(userName);
        eventEntity.setLeftSeats(eventEntity.getLeftSeats()-1);
        eventRepo.save(eventEntity);
        return true;
    }

//
}

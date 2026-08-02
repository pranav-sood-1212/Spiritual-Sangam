package com.sangam.connect.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangam.connect.entity.EventEntity;
import com.sangam.connect.entity.TicketEntity;
import com.sangam.connect.entity.UserEntity;
import com.sangam.connect.repository.EventRepo;
import com.sangam.connect.repository.TicketRepo;
import com.sangam.connect.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService {
    private final StringRedisTemplate stringRedisTemplate;
    private final EventRepo eventRepo;
    private final ObjectMapper objectMapper;
    private final UserRepo userRepo;
    private final TicketRepo ticketRepo;



    public TicketService(StringRedisTemplate stringRedisTemplate, EventRepo eventRepo, ObjectMapper objectMapper, UserRepo userRepo, TicketRepo ticketRepo) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.eventRepo = eventRepo;
        this.objectMapper = objectMapper;
        this.userRepo = userRepo;
        this.ticketRepo = ticketRepo;
    }


    @Transactional    // this is for relational datbases ---> but can work for mongo if configured....but more importantly iw will not work for redis so structure the code s.t redis thing is at last.
    public String issueTicket(UUID userId, String eventId) throws JsonProcessingException {
        UserEntity userEntity=userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Devotee profile not found"));
        EventEntity eventEntity=eventRepo.findById(eventId).orElseThrow(()->new UsernameNotFoundException("event does not exist"));
        String token=generateToken(userId,eventId);
        TicketEntity ticketEntity=new TicketEntity(userId,eventId,token);
        ticketRepo.save(ticketEntity);
        long timeToEvent=Duration.between(LocalDateTime.now(), eventEntity.getStartTime()).toHours();
        if(timeToEvent>=0&&timeToEvent<24){
            stringRedisTemplate.opsForValue().set("user:ticket:"+userId,token,Duration.ofHours(30)); // never use string.value of directly to create jave object to string it can create inconsistency in data
            // we can use either override string.valueof by lombok or best is object mapper.
        }
        return ("congrats you booked your seat");
    }

    private String generateToken(UUID userId, String eventId) {
        String uniqueToken = "SANGAM_" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return uniqueToken;
    }
    @Transactional
    public Boolean verifyAndBurnTicket(UUID userId,String eventId, String presentedToken){
        String cachedToken=stringRedisTemplate.opsForValue().get("user:ticket:"+userId);
        if(cachedToken!=null&&cachedToken.equals(presentedToken)){
            stringRedisTemplate.delete("user:ticket:"+userId);
            TicketEntity ticket = ticketRepo.findByTicketToken(presentedToken)
                    .orElseThrow(() -> new UsernameNotFoundException("Ticket reference mismatch in database"));
            ticketRepo.deleteById(ticket.getTicketId());
            return true;  /// == compares the memory addresses .equals is used to compare the actual text
        }
        else{
            TicketEntity fetchedTicket=ticketRepo.findByTicketToken(presentedToken).orElseThrow(()->new UsernameNotFoundException("no ticket with this token exists"));
            ticketRepo.deleteById(fetchedTicket.getTicketId());
            if(fetchedTicket.getTicketToken().equals(presentedToken)) return true;
        }
        return false;

    }
}

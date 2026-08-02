package com.sangam.connect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sangam.connect.Requests.TicketBookingRequest;
import com.sangam.connect.Requests.TicketVerificationRequest;
import com.sangam.connect.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> book(@RequestBody TicketBookingRequest ticketBookingRequest){
        try{
            String ticketToken=ticketService.issueTicket(ticketBookingRequest.getUserId(),ticketBookingRequest.getEventId());
            return new ResponseEntity<>(ticketToken,HttpStatus.OK);
        }catch (JsonProcessingException e){
            return new ResponseEntity<>("error in processing json of fetched ticket token from service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody TicketVerificationRequest ticketVerificationRequest) {
        UUID userId=ticketVerificationRequest.getUserId();
        String eventId=ticketVerificationRequest.getEventId();
        String token=ticketVerificationRequest.getAvailableToken();
        Boolean isVerified = ticketService.verifyAndBurnTicket(userId, eventId, token);

        if (isVerified) {
            return new ResponseEntity<>("Access Granted. Ticket Burned successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Access Denied. Invalid or used token value.", HttpStatus.UNAUTHORIZED);
        }
    }
}

package com.sangam.connect.entity;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class TicketEntity {
    public TicketEntity(){}  // empty constructor for hibernate reflection

    public TicketEntity(UUID userId, String eventId, String ticketToken) {
        this.userId = userId;
        this.eventId = eventId;
        this.ticketToken = ticketToken;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ticket_id", updatable = false, nullable = false)
    private UUID ticketId;

    @Column(name = "user_id", nullable = false)
    private UUID userId; // Pure ID link to MySQL User

    @Column(name = "event_id", nullable = false)
    private String eventId; // Pure ID link to MongoDB Event

    @Column(name = "ticket_token", columnDefinition = "CHAR(32)", nullable = false)
    private String ticketToken;

    // --- Getters and Setters ---
    public UUID getTicketId() { return ticketId; }
    public void setTicketId(UUID ticketId) { this.ticketId = ticketId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getTicketToken() { return ticketToken; }
    public void setTicketToken(String ticketToken) { this.ticketToken = ticketToken; }
}
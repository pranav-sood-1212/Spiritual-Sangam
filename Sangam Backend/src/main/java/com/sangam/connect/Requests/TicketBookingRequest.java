package com.sangam.connect.Requests;


import java.util.UUID;

public class TicketBookingRequest {
    private UUID userId;
    private String eventId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

}

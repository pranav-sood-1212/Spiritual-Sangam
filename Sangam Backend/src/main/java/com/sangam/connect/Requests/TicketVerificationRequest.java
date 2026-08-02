package com.sangam.connect.Requests;

import java.util.UUID;

public class TicketVerificationRequest {
    private UUID userId;
    private String eventId;
    private String availableToken;

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

    public String getAvailableToken() {
        return availableToken;
    }

    public void setAvailableToken(String availableToken) {
        this.availableToken = availableToken;
    }
}

package com.sangam.connect.entity;

import com.mongodb.lang.NonNull;
import com.sangam.connect.enums.EventType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Document(collection = "events")
public class EventEntity {
    @Id
    private String eventId;
    @NonNull
    private String eventTitle;
    private String description;
    @NonNull
    private EventType eventType;
    @NonNull
    private LocalDate expectedDate;
    @NonNull
    private LocalDateTime startTime;
    @NonNull
    private LocalDateTime endTime;
    private String address;
    private Location location;
    private int totalSeats;
    private int leftSeats;
    private Set<String> registeredUserNames=new HashSet<>();


    public Set<String> getRegisteredUserNames() {
        return registeredUserNames;
    }

    public void setRegisteredUserNames(Set<String> registeredUserNames) {
        this.registeredUserNames = registeredUserNames;
    }







    @NonNull
    public String getEventId() {
        return eventId;
    }

    public void setEventId(@NonNull String eventId) {
        this.eventId = eventId;
    }

    @NonNull
    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(@NonNull String eventTitle) {
        this.eventTitle = eventTitle;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(@NonNull EventType eventType) {
        this.eventType = eventType;
    }

    @NonNull
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(@NonNull LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @NonNull
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(@NonNull LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getLeftSeats() {
        return leftSeats;
    }

    public void setLeftSeats(int leftSeats) {
        this.leftSeats = leftSeats;
    }

    @NonNull
    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(@NonNull LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }
}

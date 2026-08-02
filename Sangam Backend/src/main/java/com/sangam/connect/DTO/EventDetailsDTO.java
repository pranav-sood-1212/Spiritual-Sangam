package com.sangam.connect.DTO;

import com.sangam.connect.entity.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventDetailsDTO(String eventId,
                              String eventTitle,
                              String eventDescription,
                              LocalDate expectedDate,
                              LocalDateTime startTime,
                              LocalDateTime endTime,
                              String address,
                              Location location,
                              int totalSeats,
                              int leftSeats
                              ) {
}

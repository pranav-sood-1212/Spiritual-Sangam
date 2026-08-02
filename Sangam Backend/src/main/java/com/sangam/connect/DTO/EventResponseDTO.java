package com.sangam.connect.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record EventResponseDTO(String eventId,
                               String eventTitle,
                               LocalDate expectedDate,
                               LocalDateTime startTime,
                               LocalDateTime endTime
)
{}
